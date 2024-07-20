package ssafy.closetoyou.domain.refreshtoken.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ssafy.closetoyou.domain.refreshtoken.domain.RefreshToken;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    @Query("SELECT r.userId FROM RefreshToken r WHERE r.refreshToken = :refreshToken")
    Optional<Long> findUserIdByRefreshToken(@Param("refreshToken") String refreshToken);

    Optional<RefreshToken> findByRefreshToken(String refreshToken);

    @Query("SELECT r FROM RefreshToken r WHERE r.userId = :userId")
    Optional<RefreshToken> findByUserId(@Param("userId") Long userId);

    @Modifying
    @Transactional
    @Query("UPDATE RefreshToken r SET r.refreshToken = :newRefreshToken WHERE r.userId = :userId")
    int updateRefreshTokenByUserId(@Param("userId") Long userId, @Param("newRefreshToken") String newRefreshToken);
}
