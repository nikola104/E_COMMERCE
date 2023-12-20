package eCommerce.com.eCommerce.controller;

import eCommerce.com.eCommerce.dto.request.UserAddressRequest;
import eCommerce.com.eCommerce.model.UserAddress;
import eCommerce.com.eCommerce.service.UserAddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user-address")
public class UserAddressController {
    private final UserAddressService userAddressService;

    public UserAddressController(UserAddressService userAddressService) {
        this.userAddressService = userAddressService;
    }

    @PostMapping("/save-user-address")
    public ResponseEntity<UserAddress> saveUserAddress(@RequestBody @Valid UserAddressRequest userAddressRequest){

        return new ResponseEntity<>(userAddressService.saveUserAddress(userAddressRequest), HttpStatus.CREATED);
    }


}
