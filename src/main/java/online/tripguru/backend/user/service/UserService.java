package online.tripguru.backend.user.service;

import online.tripguru.backend.common.response.Response;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<Response<?>> getUser(String userId);
}
