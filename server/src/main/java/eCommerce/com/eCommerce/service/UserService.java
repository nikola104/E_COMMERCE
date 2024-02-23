package eCommerce.com.eCommerce.service;

import eCommerce.com.eCommerce.dto.UserInfoDto;
import eCommerce.com.eCommerce.model.User;
import eCommerce.com.eCommerce.model.UserAddress;
import org.springframework.security.core.Authentication;

import java.util.List;


public interface UserService {

    public void save(User user);
    public User findByEmail(String email);
    public void existsByEmail(String email);

    List<User> getAllUsers();

    UserInfoDto getUserDtoById(Long id);

    User getUserById(Long userId);

    String deleteUserById(Long id);
    Long getUserIdByEmail(String email);

    Long findUserIdByAuthentication(Authentication authentication);
    void enableUserAccount(String email);
}
