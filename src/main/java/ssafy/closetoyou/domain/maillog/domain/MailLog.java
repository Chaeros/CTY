package ssafy.closetoyou.domain.maillog.domain;

import jakarta.persistence.*;
import lombok.*;
import ssafy.closetoyou.domain.maillog.domain.enums.MessageType;
import ssafy.closetoyou.domain.user.domain.User;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class MailLog {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "mail_log_id")
    private Long id; // key value

    private String email; // 이메일

    @Column(name = "valid_time")
    private int validTime; // 유효시간

    @Column(name = "certification_number")
    private int certificationNumber; // 인증번호

    @Column(name = "is_verification")
    private boolean isVerification; // 인증여부

    @Column(name = "message_type")
    @Enumerated(EnumType.STRING)
    private MessageType messageType; // 로그 타입(AUTHENTICATION, PASSWORD, MARKETING)

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(updatable = false)
    private LocalDateTime regdate; // 등록된 일시

    @PrePersist
    protected void onCreate() {
        this.regdate = LocalDateTime.now();
    }
}
