package eCommerce.com.eCommerce.controller;

import eCommerce.com.eCommerce.dto.UserPaymentDto;
import eCommerce.com.eCommerce.dto.request.UserPaymentRequest;
import eCommerce.com.eCommerce.service.UserPaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user-payment")
public class UserPaymentController {
    private final UserPaymentService userPaymentService;


    public UserPaymentController(UserPaymentService userPaymentService) {
        this.userPaymentService = userPaymentService;
    }

    @PostMapping("/save-user-payment")
    public ResponseEntity<String> saveUserPayment(@RequestBody @Valid UserPaymentRequest userPaymentRequest){
        return new ResponseEntity<>(userPaymentService.saveUserPayment(userPaymentRequest), HttpStatus.CREATED);
    }
    @GetMapping("/get-all-user-payments/{userId}")
    public ResponseEntity<List<UserPaymentDto>> getAllUserPayments(@PathVariable Long userId){
        return new ResponseEntity<>(userPaymentService.getAllUserPayments(userId),HttpStatus.OK);
    }
    @PatchMapping("/set-as-default-payment/{userCardNumber}")
    public ResponseEntity<String> setAsDefaultPayment(@PathVariable String userCardNumber){
        return new ResponseEntity<>(userPaymentService.setAsDefaultPayment(userCardNumber),HttpStatus.OK);
    }
    @GetMapping("/get-user-payment/{cardNumber}")
    public ResponseEntity<UserPaymentDto> getUserPayment(@PathVariable String cardNumber){
        return new ResponseEntity<>(userPaymentService.getUserPaymentById(cardNumber),HttpStatus.OK);
    }
    @DeleteMapping("/delete-user-payment/{cardNumber}")
    public ResponseEntity<String> deleteUserPayment(@PathVariable String cardNumber){
        return new ResponseEntity<>(userPaymentService.deleteUserPayment(cardNumber),HttpStatus.OK);
    }

    @PutMapping("/update-user-payment/{cardNumber}")
    public ResponseEntity<String> updateUserPayment(@RequestBody @Valid UserPaymentRequest userPaymentRequest, @PathVariable String cardNumber){
        return new ResponseEntity<>(userPaymentService.updateUserPayment(userPaymentRequest,cardNumber),HttpStatus.OK);
    }

}
