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
@Table(name = "user_history")
public class UserHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user; // Foydalanuvchi kimligi

    @Column(name = "action", nullable = false)
    private String action; // "SCAN" yoki "GENERATE" kabi harakat turi

    @Column(name = "action_time", nullable = false)
    private LocalDateTime actionTime; // Harakat qachon amalga oshirilganligi
}
