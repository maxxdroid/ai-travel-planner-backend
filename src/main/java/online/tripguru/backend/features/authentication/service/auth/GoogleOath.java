package online.tripguru.backend.features.authentication.service.auth;

import online.tripguru.backend.common.response.Response;
import online.tripguru.backend.features.authentication.dto.request.GoogleAuthRequest;
import org.springframework.http.ResponseEntity;

public interface GoogleOath {
    ResponseEntity<Response<?>> authenticate(GoogleAuthRequest request);
}
