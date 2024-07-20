package ssafy.closetoyou.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ssafy.closetoyou.domain.user.domain.User;
import ssafy.closetoyou.global.oauth.OauthServerType;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email);

    Optional<User> findByOauthServerTypeAndSocialId(OauthServerType oauthServerType, String socialId);

    Optional<User> findByOauthServerTypeAndEmail(OauthServerType oauthServerType, String email);

    Optional<User> findByEmailAndOauthServerType(String email, OauthServerType oauthServerType);

    @Query("SELECT count(*) FROM User u WHERE u.email = :email and u.passphrase = :passphrase")
    Optional<Integer> findUserIdByEmailAndPassphrase(@Param("email") String email, @Param("passphrase") String passpharse);
}
