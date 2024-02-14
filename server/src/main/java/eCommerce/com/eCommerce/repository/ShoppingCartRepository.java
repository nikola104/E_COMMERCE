package eCommerce.com.eCommerce.repository;

import eCommerce.com.eCommerce.model.ShoppingCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {

    Optional<ShoppingCart> findByUserId(Long userId);
}
