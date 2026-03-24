package online.tripguru.backend.features.authentication.controller;

import lombok.RequiredArgsConstructor;
import online.tripguru.backend.common.response.Response;
import online.tripguru.backend.features.authentication.dto.request.GoogleAuthRequest;
import online.tripguru.backend.features.authentication.dto.request.LoginRequest;
import online.tripguru.backend.features.authentication.dto.request.Refresh;
import online.tripguru.backend.features.authentication.dto.request.SignUpRequest;
import online.tripguru.backend.features.authentication.entity.GuruUserDetails;
import online.tripguru.backend.features.authentication.service.auth.AuthService;
import online.tripguru.backend.features.authentication.service.auth.GoogleOath;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final GoogleOath googleOath;
    private final AuthService authService;

    @PostMapping("login")
    ResponseEntity<Response<?>> login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);
    }

    @PostMapping("signup")
    ResponseEntity<Response<?>> signup(@RequestBody SignUpRequest signUpRequest) {
        return authService.signup(signUpRequest);
    }

    @PostMapping("refresh")
    public ResponseEntity<Response<?>> refresh(@RequestBody Refresh refresh) {
        return authService.refresh(refresh);
    }

    @PostMapping("logout")
    public ResponseEntity<Response<?>> logout() {
        return authService.logout();
    }

    @PostMapping("login/google")
    ResponseEntity<Response<?>> googleLogin(GoogleAuthRequest googleAuthRequest) {
        return googleOath.authenticate(googleAuthRequest);
    }

}
