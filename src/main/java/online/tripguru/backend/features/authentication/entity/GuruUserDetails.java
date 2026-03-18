package online.tripguru.backend.features.authentication.entity;

import lombok.Getter;
import online.tripguru.backend.user.entity.GuruUser;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class GuruUserDetails implements UserDetails {

    @Getter
    private final String id;

    @Getter
    private final String email;

    @Getter
    private final String fullName;

    private final String password;

    public GuruUserDetails (GuruUser user) {
        this.id = user.getId();
        this.email = user.getEmail();
        this.fullName = user.getName();
        this.password = user.getPassword();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return password;
    }

    /// Note: Using email as the unique field and main identifier
    @Override
    public String getUsername() {
        return email;
    }
}
