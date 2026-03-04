package online.tripguru.backend.user.service;

import lombok.RequiredArgsConstructor;
import online.tripguru.backend.common.factories.ResponseFactory;
import online.tripguru.backend.common.response.Response;
import online.tripguru.backend.user.dto.response.UserResponse;
import online.tripguru.backend.user.entity.GuruUser;
import online.tripguru.backend.user.repository.GuruUserRepository;
import online.tripguru.backend.user.utility.UserFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final GuruUserRepository guruUserRepository;

    @Override
    public ResponseEntity<Response<?>> getUser(String userId) {

        GuruUser user = guruUserRepository.findById(userId).orElseThrow(RuntimeException::new);

        try {
            UserResponse response = UserFactory.toResponse(user);

//            return ResponseEntity.status(HttpStatus.OK).body(ResponseFactory.success(response));
            
            return null;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
