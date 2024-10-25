package sarik.dev.qrbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sarik.dev.qrbot.entity.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {
    // Xabarlarga oid maxsus query'lar kerak bo'lsa, shu yerda yoziladi
}
