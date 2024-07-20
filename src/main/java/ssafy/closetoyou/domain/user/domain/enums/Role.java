package ssafy.closetoyou.domain.user.domain.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Role {
    GUEST("ROLE_GUEST","GUEST"),
    USER("ROLE_USER", "USER");

    private final String key;
    private final String roleName; // "ROLE_" 접두사가 없는 역할 이름
}
