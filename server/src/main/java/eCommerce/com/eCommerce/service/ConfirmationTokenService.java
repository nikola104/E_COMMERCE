package eCommerce.com.eCommerce.service;

import eCommerce.com.eCommerce.model.ConfirmationToken;

import java.util.Optional;

public interface ConfirmationTokenService {

    ConfirmationToken getToken(String token);

    void setConfirmedAt(String token);

    void saveConfirmationToken(ConfirmationToken confirmationToken);
}
