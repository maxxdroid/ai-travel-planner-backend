package online.tripguru.backend.user.repository;

import online.tripguru.backend.user.entity.GuruUser;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface GuruUserRepository extends MongoRepository<GuruUser, String> {
    Optional<GuruUser> findByEmail(String email);
}
