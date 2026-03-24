package online.tripguru.backend.features.authentication.service.auth;

import lombok.RequiredArgsConstructor;
import online.tripguru.backend.features.authentication.entity.RefreshToken;
import online.tripguru.backend.features.authentication.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository repository;

    public RefreshToken create(String userId, String token, String deviceId) {

        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .userId(userId)
                .deviceId(deviceId)
                .revoked(false)
                .expiryDate(Instant.now().plusSeconds(60 * 60 * 24 * 30))
                .build();

        return repository.save(refreshToken);
    }

    public RefreshToken validate(String token) {

        RefreshToken stored = repository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Invalid refresh token"));

        if (stored.isRevoked()) throw new RuntimeException("Token revoked");

        if (stored.getExpiryDate().isBefore(Instant.now()))
            throw new RuntimeException("Token expired");

        return stored;
    }

    public void revokeAll(String userId) {
        repository.deleteByUserId(userId);
    }
}