package eCommerce.com.eCommerce.controller;

import eCommerce.com.eCommerce.dto.UserAddressDto;
import eCommerce.com.eCommerce.dto.request.UserAddressRequest;
import eCommerce.com.eCommerce.model.UserAddress;
import eCommerce.com.eCommerce.service.UserAddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/user-address")
public class UserAddressController {
    private final UserAddressService userAddressService;

    public UserAddressController(UserAddressService userAddressService) {
        this.userAddressService = userAddressService;
    }

    @PostMapping("/save-user-address")
    public ResponseEntity<UserAddress> saveUserAddress(@RequestBody @Valid UserAddressRequest userAddressRequest, Authentication authentication){

        return new ResponseEntity<>(userAddressService.saveUserAddress(userAddressRequest, authentication), HttpStatus.CREATED);
    }

   @GetMapping("/get-user-address")
    public ResponseEntity<UserAddressDto> getUserAddress(Authentication authentication){
        return new ResponseEntity<>(userAddressService.getUserAddress(authentication), HttpStatus.OK);
    }
    @DeleteMapping("/delete-user-address")
    public ResponseEntity<String> deleteUserAddress(Authentication authentication){
        return new ResponseEntity<>(userAddressService.deleteUserAddress(authentication), HttpStatus.OK);
    }
    @PutMapping("/update-user-address")
    public ResponseEntity<String> updateUserAddress(@RequestBody @Valid UserAddressRequest userAddressRequest, Authentication authentication){
        return new ResponseEntity<>(userAddressService.updateUserAddress(userAddressRequest, authentication), HttpStatus.OK);
    }


}
