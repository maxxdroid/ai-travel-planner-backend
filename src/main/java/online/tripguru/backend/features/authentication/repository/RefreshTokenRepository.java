package online.tripguru.backend.features.authentication.repository;

import online.tripguru.backend.features.authentication.entity.RefreshToken;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends MongoRepository<RefreshToken, String> {

    Optional<RefreshToken> findByToken(String token);

    void deleteByUserId(String userId);
}