package ssafy.closetoyou.global.login.userdetail;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ssafy.closetoyou.domain.user.domain.User;

import java.util.Collection;
import java.util.List;

public class CustomUserDetail implements UserDetails {

    private User user;

    public CustomUserDetail(User user) {
        this.user = user;
    }

    public User getUser(){
        return user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return user.getPassphrase();
    }

    @Override
    public String getUsername() {
        return "";
    }

    public Long getUserId(){
        return user.getId();
    }
}
