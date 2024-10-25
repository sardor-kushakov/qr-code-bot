package sarik.dev.qrbot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import sarik.dev.qrbot.entity.QrCode;

@Repository
public interface QrCodeRepository extends JpaRepository<QrCode, Long> {
    // QR kodlarga oid maxsus query'lar shu yerda yoziladi
}
