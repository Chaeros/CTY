package ssafy.closetoyou.domain.user.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;
import ssafy.closetoyou.domain.maillog.domain.MailLog;
import ssafy.closetoyou.domain.user.domain.enums.Role;
import ssafy.closetoyou.domain.user.dto.request.UserModifyRequestDto;
import ssafy.closetoyou.domain.user.dto.response.UserResponseDto;
import ssafy.closetoyou.global.oauth.OauthServerType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = PROTECTED)
public class User {

    @Id @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id; // 사용자의 KEY값으로 사용될 아이디

    private String name; // 이름

    private String passphrase;

    private String email; // 이메일

    @Column(name = "social_id")
    private String socialId; // 소셜 아이디(소셜 로그인 서버에서 제공해주는 고유 키값)

    @JsonFormat(shape= JsonFormat.Shape.STRING, pattern="yyyy-MM-dd", timezone="Asia/Seoul")
    private LocalDateTime regdate; // 가입일자

    @Enumerated(EnumType.STRING)
    @Column(name = "oauth_server_type")
    private OauthServerType oauthServerType; // 소셜로그인을 했을 경우 소셜 로그인 서버(KAKAO, NAVER, GOGOLE)

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MailLog> emailLogs = new ArrayList<>();

    @Builder
    public User(Long id,
                String name,
                String email,
                String passphrase,
                String socialId,
                LocalDateTime regdate,
                OauthServerType oauthServerType,
                Role role) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.passphrase = passphrase;
        this.socialId = socialId;
        this.regdate = regdate;
        this.oauthServerType = oauthServerType;
        this.role = role;
    }

    public static User createUser(
            String name,
            String email,
            String passphrase,
            String socialId,
            LocalDateTime regdate,
            OauthServerType oauthServerType,
            Role role
    ){
        return builder()
                .name(name)
                .email(email)
                .passphrase(passphrase)
                .socialId(socialId)
                .regdate(regdate)
                .oauthServerType(oauthServerType)
                .role(role)
                .build();
    }

    public UserResponseDto transferToUserResponseDto(){
        return UserResponseDto.builder()
                .id(id)
                .name(name)
                .email(email)
                .regdate(regdate)
                .oauthServerType(oauthServerType)
                .role(role)
                .build();
    }

    public void updateUserInfo(UserModifyRequestDto userModifyRequestDto) {
        this.name = userModifyRequestDto.name();
    }

    public void passwordEncode(PasswordEncoder passwordEncoder) {
        this.passphrase = passwordEncoder.encode(this.passphrase);
    }

    public void changeRole(Role role) {
        this.role = role;
    }
}
