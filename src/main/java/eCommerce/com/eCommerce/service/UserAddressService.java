package eCommerce.com.eCommerce.service;

import eCommerce.com.eCommerce.dto.request.UserAddressRequest;
import eCommerce.com.eCommerce.model.UserAddress;

public interface UserAddressService {


    UserAddress saveUserAddress(UserAddressRequest userAddressRequest);
}
