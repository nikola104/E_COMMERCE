package eCommerce.com.eCommerce.repository;

import eCommerce.com.eCommerce.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByProductId(Long productId);
    List<CartItem> findAllByShoppingCartId(Long shoppingCartId);
}
