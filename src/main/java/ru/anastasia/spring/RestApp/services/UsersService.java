package ru.anastasia.spring.RestApp.services;

import jakarta.transaction.Transactional;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.anastasia.spring.RestApp.dto.UsersDTO;
import ru.anastasia.spring.RestApp.exception.user.UserNotCreatedException;
import ru.anastasia.spring.RestApp.exception.user.UserNotFoundException;
import ru.anastasia.spring.RestApp.models.Role;
import ru.anastasia.spring.RestApp.models.Users;
import ru.anastasia.spring.RestApp.repositories.UsersRepository;

import java.util.List;

@Service
@Transactional
public class UsersService {
    final UsersRepository usersRepository;

    public UsersService(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    public void createUser (Users users){
        if(usersRepository.findByLogin(users.getLogin()).isPresent()){
            throw new UserNotCreatedException("Пользователь с таким логином уже существует!");
        }
        users.setPassword(new BCryptPasswordEncoder(12).encode(users.getPassword()));
        users.setRole(Role.ROLE_USER);
        users.setId(null);
        usersRepository.save(users);
    }

    public List<Users> getAll(){
        return usersRepository.findAll();
    }

    public Users getById(Long id){
        return usersRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public Users getByLogin(String login){
        return usersRepository.findByLogin(login).orElseThrow(UserNotFoundException::new);
    }

    public void updateUser(UsersDTO usersDTO, String login){
        Users users = usersRepository.findByLogin(login).orElseThrow(UserNotFoundException::new);
        users.setLogin(usersDTO.getLogin());
        users.setEmail(usersDTO.getEmail());
        users.setPassword(new BCryptPasswordEncoder(12).encode(usersDTO.getPassword()));
        usersRepository.save(users);
    }

    public void deleteById (Long id){
        usersRepository.deleteById(id);
    }

    public void deleteByLogin(String login){
        usersRepository.deleteByLogin(login);
    }
}
