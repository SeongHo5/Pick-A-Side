package im.pickside.server.user.dto;

import im.pickside.server.exception.NotFoundException;
import im.pickside.server.user.enums.UserRole;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import im.pickside.server.user.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import static im.pickside.server.exception.ExceptionStatus.NOT_FOUND_ACCOUNT;


@Getter
public class UserDetailsImpl implements UserDetails, Serializable {

    private final User user;

    private final String email;

    public UserDetailsImpl(User user) {
        if (user == null) {
            throw new NotFoundException(NOT_FOUND_ACCOUNT);
        }
        this.user = user;
        this.email = user.getEmail();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        UserRole userRole = user.getUserRole();
        String authority = userRole.getAuthority();
        SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority(authority);
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(simpleGrantedAuthority);
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
