package eCommerce.com.eCommerce.controller;

import eCommerce.com.eCommerce.dto.UserAddressDto;
import eCommerce.com.eCommerce.dto.request.UserAddressRequest;
import eCommerce.com.eCommerce.model.UserAddress;
import eCommerce.com.eCommerce.service.UserAddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

   @GetMapping("/get-user-address/{id}")
    public ResponseEntity<UserAddressDto> getUserAddress(@PathVariable Long id){
        return new ResponseEntity<>(userAddressService.getUserAddress(id), HttpStatus.OK);
    }
    @DeleteMapping("/delete-user-address/{id}")
    public ResponseEntity<String> deleteUserAddress(@PathVariable Long id){
        return new ResponseEntity<>(userAddressService.deleteUserAddress(id), HttpStatus.OK);
    }
    @PutMapping("/update-user-address/{id}")
    public ResponseEntity<String> updateUserAddress(@PathVariable Long id, @RequestBody @Valid UserAddressRequest userAddressRequest){
        return new ResponseEntity<>(userAddressService.updateUserAddress(id, userAddressRequest), HttpStatus.OK);
    }


}
