package eCommerce.com.eCommerce.repository;

import eCommerce.com.eCommerce.model.UserAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserAddressRepository extends JpaRepository<UserAddress, Long> {

        Optional<UserAddress> findByUserId(Long id);
        @Transactional
        void deleteByUserId(Long id);

}
