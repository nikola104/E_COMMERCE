package eCommerce.com.eCommerce.service.impl;


import eCommerce.com.eCommerce.dto.request.UserAddressRequest;
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


}
