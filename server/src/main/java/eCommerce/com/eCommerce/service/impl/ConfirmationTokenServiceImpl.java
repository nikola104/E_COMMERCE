package eCommerce.com.eCommerce.service.impl;

import eCommerce.com.eCommerce.model.ConfirmationToken;
import eCommerce.com.eCommerce.repository.ConfirmationTokenRepository;
import eCommerce.com.eCommerce.service.ConfirmationTokenService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {
   private final ConfirmationTokenRepository confirmationTokenRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(ConfirmationTokenService.class);
    public void saveConfirmationToken(ConfirmationToken token){
        confirmationTokenRepository.save(token);
    }

    public ConfirmationToken getToken(String token) {
        LOGGER.info("Getting token for confirmation: {}", token);

        return confirmationTokenRepository.findByToken(token).orElseThrow(() -> new IllegalStateException("Token not found"));
    }

    public void setConfirmedAt(String token) {
        ConfirmationToken t = confirmationTokenRepository.findByToken(token).get();
        t.setConfirmedAt(LocalDateTime.now());
        confirmationTokenRepository.save(t);
    }
}
