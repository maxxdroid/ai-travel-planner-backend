package online.tripguru.backend.common.util;

import lombok.RequiredArgsConstructor;
import online.tripguru.backend.user.entity.GuruUser;
import online.tripguru.backend.user.repository.GuruUserRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuruUserUtil {
    private final GuruUserRepository guruUserRepository;

    public GuruUser saveGuruUser(GuruUser user){
        return guruUserRepository.save(user);
    }

    public Boolean emailAlreadyExists (String email) {
        return guruUserRepository.existsByEmail(email);
    }
}
