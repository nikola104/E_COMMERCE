package eCommerce.com.eCommerce.service;

import eCommerce.com.eCommerce.dto.UserPaymentDto;
import eCommerce.com.eCommerce.dto.request.UserPaymentRequest;
import eCommerce.com.eCommerce.model.UserPayment;

import java.util.List;

public interface UserPaymentService {
    String saveUserPayment(UserPaymentRequest userPaymentRequest);

    List<UserPaymentDto> getAllUserPayments(Long userId);

    String setAsDefaultPayment(String userCardNumber);

    UserPaymentDto getUserPaymentById(String cardNumber);
}
