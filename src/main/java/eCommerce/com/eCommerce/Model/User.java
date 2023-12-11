package eCommerce.com.eCommerce.Model;

import eCommerce.com.eCommerce.Enum.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.Stack;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Role role;
    @Column(nullable = false)
    private Boolean isVerified;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = true)
    private Stack<Product> lastSeenProducts;
    @OneToOne(cascade = CascadeType.ALL, optional = true)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private UserAddress userAddress;
    @OneToMany(mappedBy = "user")
    private Set<UserPayment> userPayments;
    @OneToOne(mappedBy = "user")
    private ShoppingCart shoppingCart;
    @OneToOne(mappedBy = "user",cascade = CascadeType.ALL)
    private OrderDetails orderDetails;




}
