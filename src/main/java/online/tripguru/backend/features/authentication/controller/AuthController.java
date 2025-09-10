package online.tripguru.backend.features.authentication.controller;

import lombok.RequiredArgsConstructor;
import online.tripguru.backend.common.response.Response;
import online.tripguru.backend.features.authentication.dto.request.GoogleAuthRequest;
import online.tripguru.backend.features.authentication.service.auth.GoogleOath;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "auth")
@RequiredArgsConstructor
public class AuthController {

    private final GoogleOath googleOath;

    @PostMapping("google")
    ResponseEntity<Response<?>> login(GoogleAuthRequest googleAuthRequest) {
        return googleOath.authenticate(googleAuthRequest);
    }

}
