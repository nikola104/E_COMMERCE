package eCommerce.com.eCommerce.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="cart_item")
public class
CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private Long quantity;
    @Column
    private double price;
    @Column(nullable = false, name = "single_price")
    private Double singlePrice;
    @Column(name = "image_path")
    private String imagePath;
    @Column(nullable = false, name = "added_at")
    private LocalDateTime addedAt;
    @OneToOne
    @JoinColumn(name = "product_id", referencedColumnName = "id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "shopping_cart_id")
    private ShoppingCart shoppingCart;

}
