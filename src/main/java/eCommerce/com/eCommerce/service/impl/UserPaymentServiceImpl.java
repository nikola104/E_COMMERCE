package eCommerce.com.eCommerce.service.impl;

import eCommerce.com.eCommerce.dto.UserPaymentDto;
import eCommerce.com.eCommerce.dto.request.UserPaymentRequest;
import eCommerce.com.eCommerce.exception.DuplicateValueException;
import eCommerce.com.eCommerce.exception.UserPaymentNotFoundException;
import eCommerce.com.eCommerce.model.UserPayment;
import eCommerce.com.eCommerce.repository.UserPaymentRepository;
import eCommerce.com.eCommerce.service.UserPaymentService;
import eCommerce.com.eCommerce.service.UserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserPaymentServiceImpl implements UserPaymentService {
    private final UserPaymentRepository userPaymentRepository;
    private final UserService userService;

    public UserPaymentServiceImpl(UserPaymentRepository userPaymentRepository, UserService userService) {
        this.userPaymentRepository = userPaymentRepository;
        this.userService = userService;
    }

    @Override
    public String saveUserPayment(UserPaymentRequest userPaymentRequest) {
        List<UserPayment> userPaymentList = userPaymentRepository.findAllByUserId(userPaymentRequest.getUserId());
        for(UserPayment userPayment : userPaymentList){
            if(userPayment.getCardNumber().equals(userPaymentRequest.getCardNumber())){
                throw new DuplicateValueException("This card already exists!");
            }
        }

        var user = userService.getUserById(userPaymentRequest.getUserId());
        var userPayment = UserPayment.builder()
                .cardNumber(userPaymentRequest.getCardNumber())
                .cardName(userPaymentRequest.getCardName())
                .expiryMonth(userPaymentRequest.getExpiryMonth())
                .expiryYear(userPaymentRequest.getExpiryYear())
                .user(user)
                .holderName(userPaymentRequest.getHolderName())
                .build();


        userPaymentRepository.save(userPayment);
        return "User payment saved successfully!";

    }

    @Override
    public List<UserPaymentDto> getAllUserPayments(Long userId) {
        List<UserPayment> userPaymentList = userPaymentRepository.findAllByUserId(userId);
        if(userPaymentList.isEmpty()){
            throw new UserPaymentNotFoundException("User payments not found!");
        }
        List<UserPaymentDto> userPaymentDtoList = userPaymentList.stream()
                .map(userPayment -> UserPaymentDto.builder()
                        .cardName(userPayment.getCardName())
                        .cardNumber(userPayment.getCardNumber())
                        .expiryMonth(userPayment.getExpiryMonth())
                        .expiryYear(userPayment.getExpiryYear())
                        .holderName(userPayment.getHolderName())
                        .build())
                .collect(Collectors.toList());

        return userPaymentDtoList;
    }

    @Override
    public String setAsDefaultPayment(String userCardNumber) {
        var userPayment = userPaymentRepository.findByCardNumber(userCardNumber).orElseThrow(() -> new UserPaymentNotFoundException("User payment not found!"));
        if(userPayment.isDefaultPayment()){
            return "This card is already set as default!";
        }
        List<UserPayment> userPaymentList = userPaymentRepository.findAllByUserId(userPayment.getUser().getId());


        List<UserPayment> newUserPaymentList = userPaymentList.stream()
                .map(userPayment1 -> {
                    userPayment1.setDefaultPayment(false);
                    return userPayment1;
                })
                .collect(Collectors.toList());
        saveTheNewListInDB(newUserPaymentList);

        userPayment.setDefaultPayment(true);
        userPaymentRepository.save(userPayment);
        return "Card was set as default successfully!";
    }

    @Override
    public UserPaymentDto getUserPaymentById(String cardNumber) {
        var userPayment = userPaymentRepository.findByCardNumber(cardNumber).orElseThrow(() -> new UserPaymentNotFoundException("User payment not found!"));
        return UserPaymentDto.builder()
                .cardName(userPayment.getCardName())
                .cardNumber(userPayment.getCardNumber())
                .expiryMonth(userPayment.getExpiryMonth())
                .expiryYear(userPayment.getExpiryYear())
                .holderName(userPayment.getHolderName())
                .build();
    }

    @Override
    public String deleteUserPayment(String cardNumber) {
        var userPayment = userPaymentRepository.findByCardNumber(cardNumber).orElseThrow(() -> new UserPaymentNotFoundException("User payment not found!"));
        userPaymentRepository.deleteByCardNumber(userPayment.getCardNumber());
        return "The payment card deleted successfully!";
    }

    @Override
    public String updateUserPayment(UserPaymentRequest userPaymentRequest, String cardNumber) {
        var userPayment = userPaymentRepository.findByCardNumber(cardNumber).orElseThrow(() -> new UserPaymentNotFoundException("User payment not found!"));
        userPayment.setCardName(userPaymentRequest.getCardName());
        userPayment.setCardNumber(userPaymentRequest.getCardNumber());
        userPayment.setExpiryMonth(userPaymentRequest.getExpiryMonth());
        userPayment.setExpiryYear(userPaymentRequest.getExpiryYear());
        userPayment.setHolderName(userPaymentRequest.getHolderName());
        userPaymentRepository.save(userPayment);
        return "User payment updated successfully!";
    }

    private void saveTheNewListInDB(List<UserPayment> newUserPaymentList) {
        for(UserPayment userPayment : newUserPaymentList){
            userPaymentRepository.save(userPayment);
        }
    }
}
