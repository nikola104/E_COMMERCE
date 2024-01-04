package eCommerce.com.eCommerce.repository;


import eCommerce.com.eCommerce.model.UserPayment;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPaymentRepository extends JpaRepository<UserPayment, Long> {

    List<UserPayment> findAllByUserId(Long userId);
    Optional<UserPayment> findByCardNumber(String cardNumber);
    Optional<UserPayment> findByUserId(Long userId);
    @Transactional
    void deleteByCardNumber(String cardNumber);
}
