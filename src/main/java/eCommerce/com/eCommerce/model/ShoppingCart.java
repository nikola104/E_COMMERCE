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
@Table(name="shopping_cart")
public class ShoppingCart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private double totalPrice;
    @Column(name = "total_items",nullable = false)
    private Long totalItems;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;
    @OneToMany(mappedBy = "shoppingCart", cascade = CascadeType.ALL,orphanRemoval = true)
    private Set<CartItem> cartItems;

}
