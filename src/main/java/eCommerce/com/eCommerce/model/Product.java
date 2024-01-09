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
    private double rating;
    @Column
    private Long views;
    @Lob
    @Column(columnDefinition = "LONGBLOB",nullable = true)
    private byte[] imageData;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @ManyToOne
    @JoinColumn(name = "subcategory_id", nullable = true)
    private Subcategory subcategory;
    @ManyToOne
    @JoinColumn(name = "type_id", nullable = true)
    private Type type;
    @OneToOne(mappedBy = "product")
    private Inventory inventory;
    @OneToOne(mappedBy = "product")
    private CartItem cartItem;
    @OneToOne(mappedBy = "product")
    private OrderItem orderItem;
    @OneToMany(mappedBy = "product",cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Review> reviews;


}
