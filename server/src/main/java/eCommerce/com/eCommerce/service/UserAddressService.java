package eCommerce.com.eCommerce.service;

import eCommerce.com.eCommerce.dto.UserAddressDto;
import eCommerce.com.eCommerce.dto.request.UserAddressRequest;
import eCommerce.com.eCommerce.model.UserAddress;
import org.springframework.security.core.Authentication;

public interface UserAddressService {


    UserAddress saveUserAddress(UserAddressRequest userAddressRequest, Authentication authentication);

    UserAddressDto getUserAddress(Authentication authentication);

    String deleteUserAddress(Authentication authentication);

    String updateUserAddress(UserAddressRequest request, Authentication authentication);
}
