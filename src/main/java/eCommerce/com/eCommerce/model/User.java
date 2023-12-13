package eCommerce.com.eCommerce.model;

import eCommerce.com.eCommerce.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.Stack;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "user")
public class User implements UserDetails {
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

    //from UserDetails
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
