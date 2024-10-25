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
@Table(name = "messages")
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Foydalanuvchi kimligi

    @Column(name = "message_text", nullable = false)
    private String messageText; // Xabar matni

    @Column(name = "sent_at", nullable = false)
    private LocalDateTime sentAt; // Xabar yuborilgan vaqt
}
