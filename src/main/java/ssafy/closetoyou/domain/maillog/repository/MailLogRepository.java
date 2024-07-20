package ssafy.closetoyou.domain.maillog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.closetoyou.domain.maillog.domain.MailLog;

import java.util.Optional;

public interface MailLogRepository extends JpaRepository<MailLog,Long> {
    Optional<MailLog> findTopByEmailOrderByRegdateDesc(String email);
}
