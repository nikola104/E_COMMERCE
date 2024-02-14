package eCommerce.com.eCommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="user_payment")
public class UserPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String cardName;
    @Column(nullable = false)
    private String cardNumber;
    @Column(nullable = false)
    private String expiryMonth;
    @Column(nullable = false)
    private String expiryYear;
    @Column(nullable = false)
    private String holderName;
    @Column(nullable = false)
    private boolean defaultPayment;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

}
