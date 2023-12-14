package eCommerce.com.eCommerce.service;

import eCommerce.com.eCommerce.model.User;
import org.springframework.stereotype.Service;


public interface UserService {

    public void save(User user);
    public User findByEmail(String email);
}
