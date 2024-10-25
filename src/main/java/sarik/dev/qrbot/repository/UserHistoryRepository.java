package sarik.dev.qrbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sarik.dev.qrbot.entity.UserHistory;

@Repository
public interface UserHistoryRepository extends JpaRepository<UserHistory, Long> {
    // Foydalanuvchi harakatlariga oid query'lar yozish mumkin
}
