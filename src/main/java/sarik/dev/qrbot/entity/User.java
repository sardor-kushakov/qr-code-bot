package sarik.dev.qrbot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Foydalanuvchi ma'lumotlarini saqlash uchun klass.
 * Bu klass Telegram bot foydalanuvchilarining id, chatId, ro'yxatdan o'tish sanasi,
 * QR kodni necha marta skan qilgani va necha marta yaratganini saqlaydi.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "chat_id", nullable = false, unique = true)
    private Long chatId;

    @Column(name = "registration_date")
    private LocalDateTime registrationDate;

    // QR kodni necha marta skan qilgani
    @Column(name = "qr_scan_count", nullable = false)
    private int qrScanCount = 0;

    // QR kodni necha marta yaratgani
    @Column(name = "qr_generate_count", nullable = false)
    private int qrGenerateCount = 0;
}
