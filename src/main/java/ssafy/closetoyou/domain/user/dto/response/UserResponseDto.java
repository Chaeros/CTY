package ssafy.closetoyou.domain.user.dto.response;

import lombok.Builder;
import ssafy.closetoyou.domain.user.domain.enums.Role;
import ssafy.closetoyou.global.oauth.OauthServerType;
import java.time.LocalDateTime;

@Builder
public record UserResponseDto(Long id,String name, String email, LocalDateTime regdate,
                              OauthServerType oauthServerType, Role role) {
}
