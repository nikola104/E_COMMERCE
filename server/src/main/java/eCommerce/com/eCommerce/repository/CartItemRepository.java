package eCommerce.com.eCommerce.repository;

import eCommerce.com.eCommerce.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    Optional<CartItem> findByProductId(Long productId);
    List<CartItem> findAllByShoppingCartId(Long shoppingCartId);

    //find all items created seven days before the cutoff date
    @Query("SELECT c FROM CartItem c WHERE c.addedAt <= :cutoffDate")
    List<CartItem> findItemsCreatedSevenDaysBefore(LocalDateTime cutoffDate);

    Optional<CartItem> findByShoppingCartIdAndProductId(Long id, Long productId);


}
