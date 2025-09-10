package online.tripguru.backend.features.authentication.service.auth;

import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeTokenRequest;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import lombok.RequiredArgsConstructor;
import online.tripguru.backend.common.response.Response;
import online.tripguru.backend.features.authentication.dto.request.GoogleAuthRequest;
import online.tripguru.backend.features.authentication.util.JwtUtil;
import online.tripguru.backend.user.entity.GuruUser;
import online.tripguru.backend.user.repository.GuruUserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GoogleOathImpl implements GoogleOath {

    private final GuruUserRepository guruUserRepository;

    private final JwtUtil jwtUtil;

    private static final String TOKEN_URL = "https://oauth2.googleapis.com/token";

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.client-secret}")
    private String clientSecret;

    @Override
    public ResponseEntity<Response<?>> authenticate(GoogleAuthRequest request) {

        try {

            var tokenResponse = new GoogleAuthorizationCodeTokenRequest(
                    new NetHttpTransport(),
                    GsonFactory.getDefaultInstance(),
                    TOKEN_URL,
                    clientId,          // injected from properties
                    clientSecret,      // injected from properties
                    request.authCode(),
                    ""
            ).execute();

            GuruUser newUser = parseIdToken(tokenResponse.getIdToken());

            Optional<GuruUser> optionalGuruUser = guruUserRepository.findByEmail(newUser.getEmail());

            if (optionalGuruUser.isEmpty()) {
                guruUserRepository.save(newUser);
            }

            return ResponseEntity.status(HttpStatus.OK).body(
                    Response.builder()
                            .message("User Created")
                            .token(jwtUtil.generateToken(newUser))
                            .build()
            );

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private GuruUser parseIdToken(String idTokenString) throws Exception {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(),
                GsonFactory.getDefaultInstance())
                .setAudience(Collections.singletonList(clientId))
                .build();

        GoogleIdToken idToken = verifier.verify(idTokenString);
        if (idToken != null) {
            var payload = idToken.getPayload();
            GuruUser user = new GuruUser();
            user.setEmail(payload.getEmail());
            user.setName((String) payload.get("name"));
            user.setProfileUrl((String) payload.get("picture"));
            return user;
        } else {
            throw new RuntimeException("Invalid ID token");
        }
    }
}
