package eCommerce.com.eCommerce.controller;

import eCommerce.com.eCommerce.dto.UserInfoDto;
import eCommerce.com.eCommerce.model.User;
import eCommerce.com.eCommerce.model.UserAddress;
import eCommerce.com.eCommerce.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-all-users")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @GetMapping("/get-user-by-id/{id}")
    public ResponseEntity<UserInfoDto> getUSerById(@PathVariable Long id){
    return new ResponseEntity<>(userService.getUserDtoById(id),HttpStatus.OK);
    }
    @DeleteMapping("/delete-user-by-id/{id}")
    public ResponseEntity<String> deleteUserById(@PathVariable Long id){
        return new ResponseEntity<>(userService.deleteUserById(id),HttpStatus.OK);
    }



}
