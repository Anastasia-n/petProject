package ru.anastasia.spring.RestApp.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import ru.anastasia.spring.RestApp.models.Users;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SecurityUsers implements UserDetails {

    private final String login;
    private final String password;
    private final List<SimpleGrantedAuthority> authorities;

    public SecurityUsers(String login, String password, List<SimpleGrantedAuthority> authorities) {
        this.login = login;
        this.password = password;
        this.authorities = authorities;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

//    public static UserDetails fromUser (Users users){
//        return new User(users.getLogin(),users.getPassword(),users.getRole().getAuthorities());
//    }

    public static UserDetails fromUser (Users users){
        return new User(users.getLogin(),users.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority(users.getRole().toString())));
    }
}
