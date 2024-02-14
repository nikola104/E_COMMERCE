package eCommerce.com.eCommerce.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="order_details")
public class OrderDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private double totalPrice;
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @OneToOne(optional = false)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @OneToMany(mappedBy = "orderDetails")
    private Set<OrderItem> orderItems;
    @OneToOne(cascade = CascadeType.ALL,optional = false)
    @JoinColumn(name = "payment_details_id", referencedColumnName = "id")
    private PaymentDetails paymentDetails;
}
