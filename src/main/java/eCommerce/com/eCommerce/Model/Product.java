package eCommerce.com.eCommerce.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String productName;
    @Column(nullable = false)
    private String description;
    @Column
    private String color;
    @Column(nullable = false)
    private String sku;
    @Column
    private String material;
    @Column
    private Long views;
    @Lob
    @Column(nullable = true)
    private byte[] imageData;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "subcategory_id", nullable = true)
    private Subcategory subcategory;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "type_id", nullable = true)
    private Type type;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "inventory_id", referencedColumnName = "id")
    private Inventory inventory;
    @OneToOne(mappedBy = "product")
    private CartItem cartItem;
    @OneToOne(mappedBy = "product")
    private OrderItem orderItem;


}
