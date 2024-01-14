package ru.anastasia.spring.RestApp.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.anastasia.spring.RestApp.models.Users;
import ru.anastasia.spring.RestApp.repositories.UsersRepository;

@Service("userDetailsServiceImpl")
public class UserDetailsServiceImpl implements UserDetailsService {

    final
    UsersRepository usersRepository;

    public UserDetailsServiceImpl(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Users users = usersRepository.findByLogin(login).orElseThrow(() -> new UsernameNotFoundException("Логин не найден"));
        return SecurityUsers.fromUser(users);
    }
}