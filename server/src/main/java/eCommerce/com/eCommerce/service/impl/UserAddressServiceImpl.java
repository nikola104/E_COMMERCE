package eCommerce.com.eCommerce.service.impl;


import eCommerce.com.eCommerce.dto.UserAddressDto;
import eCommerce.com.eCommerce.dto.request.UserAddressRequest;
import eCommerce.com.eCommerce.exception.UserNotFoundException;
import eCommerce.com.eCommerce.model.UserAddress;
import eCommerce.com.eCommerce.repository.UserAddressRepository;
import eCommerce.com.eCommerce.service.UserAddressService;
import eCommerce.com.eCommerce.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class UserAddressServiceImpl implements UserAddressService {


    private final UserAddressRepository userAddressRepository;
    private final UserService userService;




    public UserAddressServiceImpl(UserAddressRepository userAddressRepository, UserService userService) {
        this.userAddressRepository = userAddressRepository;
        this.userService = userService;
    }

    private final static Logger LOGGER = LoggerFactory.
            getLogger(UserAddressServiceImpl.class);


    @Override
    public UserAddress saveUserAddress(UserAddressRequest userAddressRequest, Authentication authentication) {
        List<UserAddress> userAddressList = userAddressRepository.findAll();
        for(UserAddress userAddress : userAddressList){
            if(userAddress.getPhoneNumber().equals(userAddressRequest.getPhoneNumber())){
                throw new UserNotFoundException("This phone number already exists!");
            }
        }
        var userId = userService.findUserIdByAuthentication(authentication);

        var user = userService.getUserById(userId);
        var userAddress = UserAddress.builder()
                .phoneNumber(userAddressRequest.getPhoneNumber())
                .city(userAddressRequest.getCity())
                .country(userAddressRequest.getCountry())
                .addressLine1(userAddressRequest.getAddressLine1())
                .addressLine2(userAddressRequest.getAddressLine2())
                .user(user)
                .postcode(userAddressRequest.getPostcode())
                .build();


            return userAddressRepository.save(userAddress);


    }

    @Override
    public UserAddressDto getUserAddress(Authentication authentication) {
        var userId = userService.findUserIdByAuthentication(authentication);
        var userAddress = userAddressRepository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException("User address not found!"));


        var userAddressDto = UserAddressDto.builder()
                .addressLine1(userAddress.getAddressLine1())
                .addressLine2(userAddress.getAddressLine2())
                .phoneNumber(userAddress.getPhoneNumber())
                .country(userAddress.getCountry())
                .city(userAddress.getCity())
                .postcode(userAddress.getPostcode())
                .build();

        return userAddressDto;

    }

    @Override
    public String deleteUserAddress(Authentication authentication) {
        var userId = userService.findUserIdByAuthentication(authentication);
        var userAddress = userAddressRepository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException("User address not found!"));
        userAddressRepository.deleteByUserId(userAddress.getUser().getId());
        return "User address deleted successfully!";

    }

    @Override
    public String updateUserAddress(UserAddressRequest request, Authentication authentication) {
        var userId = userService.findUserIdByAuthentication(authentication);
        var userAddress = userAddressRepository.findByUserId(userId).orElseThrow(() -> new UserNotFoundException("User address not found!"));
        userAddress.setAddressLine1(request.getAddressLine1());
        userAddress.setAddressLine2(request.getAddressLine2());
        userAddress.setCity(request.getCity());
        userAddress.setCountry(request.getCountry());
        userAddress.setPhoneNumber(request.getPhoneNumber());
        userAddress.setPostcode(request.getPostcode());

        userAddressRepository.save(userAddress);
        return "User address updated successfully!";
    }


}
