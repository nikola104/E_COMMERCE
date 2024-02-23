package eCommerce.com.eCommerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ConfirmationToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "token")
    private String token;
    @Column(nullable = false, name="created_at")
    private LocalDateTime createdAt;
    @Column(nullable = false, name = "expires_at")
    private LocalDateTime expiresAt;
    @Column(nullable = true, name = "confirmed_at")
    private LocalDateTime confirmedAt;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
