package eCommerce.com.eCommerce.service;

import eCommerce.com.eCommerce.dto.UserAddressDto;
import eCommerce.com.eCommerce.dto.request.UserAddressRequest;
import eCommerce.com.eCommerce.model.UserAddress;

public interface UserAddressService {


    UserAddress saveUserAddress(UserAddressRequest userAddressRequest);

    UserAddressDto getUserAddress(Long id);

    String deleteUserAddress(Long id);

    String updateUserAddress(Long id, UserAddressRequest userAddressRequest);
}
