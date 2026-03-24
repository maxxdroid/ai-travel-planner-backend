package online.tripguru.backend.features.authentication.service.auth;

import lombok.RequiredArgsConstructor;
import online.tripguru.backend.common.exception.AuthExceptions;
import online.tripguru.backend.common.response.Response;
import online.tripguru.backend.common.factories.ResponseFactory;
import online.tripguru.backend.common.util.GuruUserUtil;
import online.tripguru.backend.features.authentication.dto.request.LoginRequest;
import online.tripguru.backend.features.authentication.dto.request.Refresh;
import online.tripguru.backend.features.authentication.dto.request.SignUpRequest;
import online.tripguru.backend.features.authentication.entity.GuruUserDetails;
import online.tripguru.backend.features.authentication.entity.RefreshToken;
import online.tripguru.backend.features.authentication.service.main.GuruUserDetailsImpl;
import online.tripguru.backend.features.authentication.util.JwtUtil;
import online.tripguru.backend.user.entity.GuruUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final RefreshTokenService refreshTokenService;
    private final GuruUserUtil guruUserUtil;
    private final PasswordEncoder passwordEncoder;
    private final GuruUserDetailsImpl guruUserDetails;

    @Override
    public ResponseEntity<Response<?>> login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        GuruUserDetails userDetails = (GuruUserDetails) authentication.getPrincipal();

        String accessToken = jwtUtil.generateAccessToken(userDetails);
        String refreshToken = jwtUtil.generateRefreshToken(userDetails);

        var tokens = getTokens(accessToken, refreshToken);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseFactory.tokenResponse(tokens));
    }

    @Override
    public ResponseEntity<Response<?>> signup(SignUpRequest signUpRequest) {

        if (guruUserUtil.emailAlreadyExists(signUpRequest.email())) throw new RuntimeException();

        try {

            GuruUser guruUser = GuruUser
                    .builder()
                    .password(passwordEncoder.encode(signUpRequest.password())) /// Encoding Password
                    .email(signUpRequest.email())
                    .name(signUpRequest.fullName())
                    .phone(signUpRequest.phoneNumber())
                    .build();

            GuruUser savedGuruUser = guruUserUtil.saveGuruUser(guruUser);

            GuruUserDetails userDetails = (GuruUserDetails) guruUserDetails.loadUserByUsername(savedGuruUser.getEmail());

            String accessToken = jwtUtil.generateAccessToken(userDetails);
            String refreshToken = jwtUtil.generateRefreshToken(userDetails);

            refreshTokenService.create(userDetails.getId(), refreshToken, "mobile");

            var tokens = getTokens(accessToken, refreshToken);

            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseFactory.creationsSuccess(tokens));

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ResponseEntity<Response<?>> refresh(Refresh refresh) {

        if (!jwtUtil.extractType(refresh.refreshToken()).equals("refresh")) throw new RuntimeException("Invalid token");

        RefreshToken stored = refreshTokenService.validate(refresh.refreshToken());

        GuruUserDetails user = (GuruUserDetails) guruUserDetails.loadUserByUsername(jwtUtil.extractEmail(refresh.refreshToken()));

        /// rotate
        refreshTokenService.revokeAll(user.getId());

        String newAccessToken = jwtUtil.generateAccessToken(user);
        String newRefreshToken = jwtUtil.generateRefreshToken(user);

        refreshTokenService.create(user.getId(), newRefreshToken, stored.getDeviceId());

        var tokens = getTokens(newAccessToken, newRefreshToken);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseFactory.tokenResponse(tokens));
    }

    @Override
    public ResponseEntity<Response<?>> logout() {

        /// Get current authenticated user from SecurityContext
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null) throw new AuthExceptions.UserNotFoundException();

        GuruUserDetails user = (GuruUserDetails) auth.getPrincipal();

        refreshTokenService.revokeAll(user.getId());

        return ResponseEntity.status(HttpStatus.OK).body(ResponseFactory.success().getBody());
    }

    private Map<String, String> getTokens (String access, String refresh) {
        return Map.of(
                "accessToken", access,
                "refreshToken", refresh
        );
    }
}
