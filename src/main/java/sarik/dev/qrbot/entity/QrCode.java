package sarik.dev.qrbot.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "qr_codes")
public class QrCode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "qr_data", nullable = false)
    private String qrData; // QR kodning saqlangan ma'lumotlari

    @Column(name = "created_at")
    private LocalDateTime createdAt; // QR kod qachon yaratilganligi

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // QR kodni yaratgan yoki skan qilgan foydalanuvchi
}
