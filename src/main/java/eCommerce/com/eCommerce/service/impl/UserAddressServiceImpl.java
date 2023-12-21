package eCommerce.com.eCommerce.service.impl;


import eCommerce.com.eCommerce.dto.UserAddressDto;
import eCommerce.com.eCommerce.dto.UserInfoDto;
import eCommerce.com.eCommerce.dto.request.UserAddressRequest;
import eCommerce.com.eCommerce.exception.UserNotFoundException;
import eCommerce.com.eCommerce.model.UserAddress;
import eCommerce.com.eCommerce.repository.UserAddressRepository;
import eCommerce.com.eCommerce.service.UserAddressService;
import eCommerce.com.eCommerce.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;


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
    public UserAddress saveUserAddress(UserAddressRequest userAddressRequest) {

        var user = userService.getUserById(userAddressRequest.getUser().getId());
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
    public UserAddressDto getUserAddress(Long id) {
        var userAddress = userAddressRepository.findByUserId(id).orElseThrow(() -> new UserNotFoundException("User address not found!"));
        var userDto = UserInfoDto.builder()
                .firstName(userAddress.getUser().getFirstName())
                .lastName(userAddress.getUser().getLastName())
                .email(userAddress.getUser().getEmail())
                .createdAt(userAddress.getUser().getCreatedAt())
                .role(userAddress.getUser().getRole())
                .build();

        var userAddressDto = UserAddressDto.builder()
                .addressLine1(userAddress.getAddressLine1())
                .addressLine2(userAddress.getAddressLine2())
                .phoneNumber(userAddress.getPhoneNumber())
                .country(userAddress.getCountry())
                .city(userAddress.getCity())
                .postcode(userAddress.getPostcode())
                .userInfo(userDto)
                .build();

        return userAddressDto;

    }

    @Override
    public String deleteUserAddress(Long id) {
        userAddressRepository.deleteByUserId(id);
        return "User address deleted successfully!";

    }

    @Override
    public String updateUserAddress(Long id, UserAddressRequest userAddressRequest) {
        var userAddress = userAddressRepository.findByUserId(id).orElseThrow(() -> new UserNotFoundException("User address not found!"));
        userAddress.setAddressLine1(userAddressRequest.getAddressLine1());
        userAddress.setAddressLine2(userAddressRequest.getAddressLine2());
        userAddress.setCity(userAddressRequest.getCity());
        userAddress.setCountry(userAddressRequest.getCountry());
        userAddress.setPhoneNumber(userAddressRequest.getPhoneNumber());
        userAddress.setPostcode(userAddressRequest.getPostcode());

        userAddressRepository.save(userAddress);
        return "User address updated successfully!";
    }


}
