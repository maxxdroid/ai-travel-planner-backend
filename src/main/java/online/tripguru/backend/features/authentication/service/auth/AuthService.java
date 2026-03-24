package online.tripguru.backend.features.authentication.service.auth;

import online.tripguru.backend.common.response.Response;
import online.tripguru.backend.features.authentication.dto.request.LoginRequest;
import online.tripguru.backend.features.authentication.dto.request.Refresh;
import online.tripguru.backend.features.authentication.dto.request.SignUpRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {

    ResponseEntity<Response<?>> login(LoginRequest request);

    ResponseEntity<Response<?>> signup(SignUpRequest signUpRequest);

    public ResponseEntity<Response<?>> refresh(Refresh refresh);

    ResponseEntity<Response<?>> logout();

}
