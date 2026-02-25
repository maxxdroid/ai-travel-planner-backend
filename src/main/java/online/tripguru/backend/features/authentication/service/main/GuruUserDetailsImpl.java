package online.tripguru.backend.features.authentication.service.main;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import online.tripguru.backend.common.exception.AuthExceptions;
import online.tripguru.backend.features.authentication.entity.GuruUserDetails;
import online.tripguru.backend.user.entity.GuruUser;
import online.tripguru.backend.user.repository.GuruUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class GuruUserDetailsImpl implements UserDetailsService {

    private final GuruUserRepository guruUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {

            log.info("Loading User");

            Optional<GuruUser> guruUser = guruUserRepository.findByEmail(username);

            if (guruUser.isPresent()) {
                return new GuruUserDetails(guruUser.get());
            }

            throw new AuthExceptions.UserNotFoundException();

        } catch (Exception e) {
            throw new AuthExceptions.AuthenticationFailedException();
        }
    }

}
