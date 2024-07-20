package ssafy.closetoyou.domain.refreshtoken.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = PROTECTED)
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "refresh_token_id")
    private Long id; // key ID

    @Column(name = "user_id")
    private Long userId; // 토큰 소유자의 ID

    @Column(name = "refresh_token")
    private String refreshToken; // JWT 리프레시토큰

    @Builder
    public RefreshToken(Long id, Long userId, String refreshToken) {
        this.id = id;
        this.userId = userId;
        this.refreshToken = refreshToken;
    }
}
