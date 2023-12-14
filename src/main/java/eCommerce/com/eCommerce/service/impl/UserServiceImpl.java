package eCommerce.com.eCommerce.service.impl;

import eCommerce.com.eCommerce.model.User;
import eCommerce.com.eCommerce.repository.UserRepository;
import eCommerce.com.eCommerce.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).get();
    }
}
