package eCommerce.com.eCommerce.service.impl;

import eCommerce.com.eCommerce.dto.UserInfoDto;
import eCommerce.com.eCommerce.exception.UserNotFoundException;
import eCommerce.com.eCommerce.model.User;
import eCommerce.com.eCommerce.model.UserAddress;
import eCommerce.com.eCommerce.repository.UserRepository;
import eCommerce.com.eCommerce.service.UserAddressService;
import eCommerce.com.eCommerce.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
        return userRepository.findByEmail(email).orElseThrow(() -> new UserNotFoundException("User not found!"));
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = userRepository.findAll();
        if(users.isEmpty()){
            throw new UserNotFoundException("Users not found!");
        }
        return users;
    }

    @Override
    public UserInfoDto getUserDtoById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found!"));

            return UserInfoDto.builder()
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .createdAt(user.getCreatedAt())
                    .email(user.getEmail())
                    .role(user.getRole())
                    .build();

    }



    @Override
    public User getUserById(Long userId) {
        var user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User not found!"));
        return user;
    }
}
