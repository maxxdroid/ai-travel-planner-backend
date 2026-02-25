package online.tripguru.backend.features.authentication.service.auth;

import lombok.RequiredArgsConstructor;
import online.tripguru.backend.common.response.Response;
import online.tripguru.backend.common.factories.ResponseFactory;
import online.tripguru.backend.common.util.GuruUserUtil;
import online.tripguru.backend.features.authentication.dto.request.LoginRequest;
import online.tripguru.backend.features.authentication.dto.request.SignUpRequest;
import online.tripguru.backend.features.authentication.entity.GuruUserDetails;
import online.tripguru.backend.features.authentication.util.JwtUtil;
import online.tripguru.backend.user.entity.GuruUser;
import online.tripguru.backend.user.repository.GuruUserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService{

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final GuruUserUtil guruUserUtil;
    private final PasswordEncoder passwordEncoder;

    @Override
    public ResponseEntity<Response<?>> login(LoginRequest request) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password())
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        GuruUserDetails userDetails = (GuruUserDetails) authentication.getPrincipal();

        String jwt = jwtUtil.generateToken(userDetails);

        return ResponseEntity.status(HttpStatus.OK).body(ResponseFactory.tokenResponse(jwt));
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

            guruUserUtil.saveGuruUser(guruUser);

            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseFactory.creationsSuccess());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
