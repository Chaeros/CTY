package ssafy.closetoyou.domain.maillog.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import ssafy.closetoyou.domain.maillog.domain.MailLog;
import ssafy.closetoyou.domain.maillog.domain.enums.MessageType;
import ssafy.closetoyou.domain.maillog.exception.MailLogException;
import ssafy.closetoyou.domain.maillog.exception.MailSendFailException;
import ssafy.closetoyou.domain.maillog.repository.MailLogRepository;
import ssafy.closetoyou.domain.user.exception.NotExistUserException;
import ssafy.closetoyou.domain.user.repository.UserRepository;
import ssafy.closetoyou.global.jwt.service.JwtService;
import ssafy.closetoyou.global.validator.email.EmailValidator;
import ssafy.closetoyou.global.validator.user.UserValidator;

@Service
@Slf4j
@RequiredArgsConstructor
public class MailLogService {

    private final JavaMailSender javaMailSender;
    private final MailLogRepository mailLogRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    @Value("${GOOGLE.MAIL.ADDRESS}")
    private String senderEmail;

    @Value("${SERVER.FRONT}")
    private String SERVER_FRONT;

    // 랜덤한 인증 번호 생성
    public static int createNumber() {
        return (int)(Math.random() * (90000)) + 100000; //(int) Math.random() * (최댓값-최소값+1) + 최소값
    }

    // 인증 메일 생성
    public MimeMessage createMail(String mail) {
        int number = createNumber();
        MimeMessage message = javaMailSender.createMimeMessage();
        MailLog mailLog = MailLog.builder()
                .email(mail)
                .user(userRepository.findByEmailAndOauthServerType(mail,null).orElse(null))
                .certificationNumber(number)
                .messageType(MessageType.AUTHENTICATION)
                .build();
        mailLogRepository.save(mailLog);

        try {
            message.setFrom(senderEmail);
            message.setRecipients(MimeMessage.RecipientType.TO, mail);
            message.setSubject("CloseToYou 이메일 인증");
            String body = "";
            String token = jwtService.createCertificationToken(mail,number);
            String authenticationURI = SERVER_FRONT+"/mailcertification?token="+token;
            body += "<h3>" + "요청하신 인증 URI 입니다. 버튼 클릭 시 인증이 완료됩니다." + "</h3>";
            body += "<a href=\"" + authenticationURI + "\">" + authenticationURI + "</a>";
            body += "<h3>" + "감사합니다." + "</h3>";
            message.setText(body,"UTF-8", "html");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return message;
    }

    // 최신 MailLog 조회
    public int searchTopByEmailOrderByRegDateDesc(String mail){
        return mailLogRepository.findTopByEmailOrderByRegdateDesc(mail)
                .orElseThrow(() -> new MailLogException("메일 로그를 찾을 수 없습니다."))
                .getCertificationNumber();
    }

    // 생성한 메일 사용자에게 전송
    public int sendMail(String mail) {
        EmailValidator.isValidEmail(mail);
        if(userRepository.findByEmail(mail).orElse(null)==null){
            throw NotExistUserException.EXCEPTION;
        }
        try{
            javaMailSender.send(createMail(mail));
        }
        catch (Exception e) {
            log.info("[MailLog]이메일 전송 실패 message : {}",e.getMessage());
            throw MailSendFailException.EXCEPTION;
        }
        return searchTopByEmailOrderByRegDateDesc(mail);
    }
}
