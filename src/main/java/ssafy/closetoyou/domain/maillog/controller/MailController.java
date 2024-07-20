package ssafy.closetoyou.domain.maillog.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ssafy.closetoyou.domain.maillog.dto.request.MailCheckRequestDto;
import ssafy.closetoyou.domain.maillog.dto.request.MailRequestDto;
import ssafy.closetoyou.domain.maillog.exception.InvalidCertificationNumberException;
import ssafy.closetoyou.domain.maillog.exception.MailAuthenticationFailException;
import ssafy.closetoyou.domain.maillog.exception.MailSendFailException;
import ssafy.closetoyou.domain.maillog.service.MailLogService;
import ssafy.closetoyou.domain.user.domain.User;
import ssafy.closetoyou.domain.user.domain.enums.Role;
import ssafy.closetoyou.domain.user.repository.UserRepository;
import ssafy.closetoyou.global.common.response.ApiResponse;
import ssafy.closetoyou.global.jwt.service.JwtService;
import ssafy.closetoyou.global.validator.email.EmailValidator;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/maillog")
public class MailController {
    private final MailLogService mailLogService;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    // 인증 이메일 전송
    @PostMapping("/send")
    public ApiResponse<String> mailSend(@RequestBody MailRequestDto mailRequestDto) {
        mailLogService.sendMail(mailRequestDto.mail());
        log.info("[MailLog]이메일 전송 성공 mail : {}", mailRequestDto.mail());
        return new ApiResponse<>(200,true,null,"인증 이메일 전송 성공");
    }

    // 인증번호 일치여부 확인
    @PostMapping("/check")
    public ApiResponse<Void> mailCheck(@RequestBody MailCheckRequestDto mailCheckRequestDto) {
        String token = mailCheckRequestDto.token();
        jwtService.isTokenValid(token);
        String email = jwtService.extractEmail(token).orElse(null);
        if(email==null){
            throw MailAuthenticationFailException.EXCEPTION;
        }
        String certificationNumber = String.valueOf(jwtService.extractCertificationNumber(token).orElse(null));
        boolean isMatch = certificationNumber
                .equals(String.valueOf(mailLogService.searchTopByEmailOrderByRegDateDesc(email)));
        if (isMatch) {
            User user = userRepository
                    .findByEmailAndOauthServerType(email,null)
                    .orElse(null);
            if(user==null){
                throw InvalidCertificationNumberException.EXCEPTION;
            }

            if (user.getRole() == Role.GUEST) {
                user.changeRole(Role.USER);
                userRepository.save(user);
            }

            return new ApiResponse<>(200, true, null, "인증번호가 일치합니다.");
        }
        throw InvalidCertificationNumberException.EXCEPTION;
    }
}
