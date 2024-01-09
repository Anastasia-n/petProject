package ru.anastasia.spring.RestApp.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.anastasia.spring.RestApp.dto.UsersDTO;
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
        users.setRole(Role.USER);
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

    public void updateUser(UsersDTO usersDTO, Long id){
        Users users = usersRepository.findById(id).orElseThrow(UserNotFoundException::new);
        users.setLogin(usersDTO.getLogin());
        users.setEmail(usersDTO.getEmail());
        users.setPassword(usersDTO.getPassword());
        usersRepository.save(users);
    }

    public void deleteById (Long id){
        usersRepository.deleteById(id);
    }
}
