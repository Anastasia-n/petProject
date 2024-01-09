package ru.anastasia.spring.RestApp.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.anastasia.spring.RestApp.dto.CommentDTO;
import ru.anastasia.spring.RestApp.dto.UsersDTO;
import ru.anastasia.spring.RestApp.exception.user.UserErrorResponse;
import ru.anastasia.spring.RestApp.exception.user.UserNotFoundException;
import ru.anastasia.spring.RestApp.exception.user.UserNotUpdatedException;
import ru.anastasia.spring.RestApp.models.Comment;
import ru.anastasia.spring.RestApp.models.Users;
import ru.anastasia.spring.RestApp.services.UsersService;

import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {

    private final UsersService usersService;
    private final ModelMapper modelMapper;

    public UserController(UsersService usersService, ModelMapper modelMapper) {
        this.usersService = usersService;
        this.modelMapper = modelMapper;
    }

    private UsersDTO convertToUsersDTO (Users users){
        return modelMapper.map(users, UsersDTO.class);
    }

    private Users convertToUsers (UsersDTO usersDTO){
        return modelMapper.map(usersDTO, Users.class);
    }

    @GetMapping("/user")
    public List<UsersDTO> getAllUsers(){
        return usersService.getAll().stream().map(this::convertToUsersDTO).collect(Collectors.toList());
    }

    @GetMapping("/user/{id}")
    public UsersDTO getUserById(@PathVariable("id") Long id){
        return convertToUsersDTO(usersService.getById(id));
    }

    @GetMapping("/user/search/{login}")
    public UsersDTO getUserByLogin(@PathVariable("login") String login){
        return convertToUsersDTO(usersService.getByLogin(login));
    }

    @GetMapping("/user/comments/{id}")
    public List<CommentDTO> getComments(@PathVariable("id") Long id){
        return usersService.getById(id).getCommentList()
                .stream()
                .map(n -> modelMapper.map(n, CommentDTO.class))
                .collect(Collectors.toList());
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(@PathVariable("id") Long id,
                                        @RequestBody @Valid UsersDTO usersDTO,
                                        BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder builder = new StringBuilder();
            bindingResult.getFieldErrors().forEach(n -> builder
                    .append(n.getField())
                    .append(" - ")
                    .append(n.getDefaultMessage())
                    .append(";"));
            throw new UserNotUpdatedException(builder.toString());
        }
        usersService.updateUser(usersDTO, id);
        return ResponseEntity.ok().body("Пользователь с id = " + id + " изменен");
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity<?> deleteUser (@PathVariable("id") Long id){
        usersService.deleteById(id);
        return ResponseEntity.ok().body("Пользователь с id = " + id + " удален");
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException (UserNotFoundException e){
        UserErrorResponse response = new UserErrorResponse(
                "Пользователь не найден",
                LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException (UserNotUpdatedException e){
        UserErrorResponse response = new UserErrorResponse(
                e.getMessage(),
                LocalDateTime.now());
        return new ResponseEntity<>(response,HttpStatus.BAD_REQUEST);
    }

}
