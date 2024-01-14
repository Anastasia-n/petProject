package ru.anastasia.spring.RestApp.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.anastasia.spring.RestApp.dto.CommentDTO;
import ru.anastasia.spring.RestApp.dto.UsersDTO;
import ru.anastasia.spring.RestApp.exception.user.UserErrorResponse;
import ru.anastasia.spring.RestApp.exception.user.UserNotFoundException;
import ru.anastasia.spring.RestApp.exception.user.UserNotUpdatedException;
import ru.anastasia.spring.RestApp.models.Users;
import ru.anastasia.spring.RestApp.services.UsersService;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
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


    //Получить список пользователей
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping()
    public List<UsersDTO> getAllUsers(){
        return usersService.getAll().stream().map(this::convertToUsersDTO).collect(Collectors.toList());
    }

    //Поиск пользователя по id
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public UsersDTO getUserById(@PathVariable("id") Long id){
        return convertToUsersDTO(usersService.getById(id));
    }

    //Поиск пользователя по логину
    @GetMapping("/find/{login}")
    public UsersDTO getUserByLogin(@PathVariable("login") String login){
        return convertToUsersDTO(usersService.getByLogin(login));
    }

    //Получить комментарии пользователя
    @GetMapping("/comments/{id}")
    public List<CommentDTO> getComments(@PathVariable("id") Long id){
        return usersService.getById(id).getCommentList()
                .stream()
                .map(n -> modelMapper.map(n, CommentDTO.class))
                .collect(Collectors.toList());
    }

    //Редактирование пользователя
    @PutMapping("/update")
    public ResponseEntity<?> updateUser(@RequestBody @Valid UsersDTO usersDTO,
                                        BindingResult bindingResult,
                                        Principal principal){
        if(bindingResult.hasErrors()){
            StringBuilder builder = new StringBuilder();
            bindingResult.getFieldErrors().forEach(n -> builder
                    .append(n.getField())
                    .append(" - ")
                    .append(n.getDefaultMessage())
                    .append(";"));
            throw new UserNotUpdatedException(builder.toString());
        }
        usersService.updateUser(usersDTO, principal.getName());
        return ResponseEntity.ok().body("Пользователь изменен");
    }

    //Удаление пользователя по id
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser (@PathVariable("id") Long id){
        usersService.deleteById(id);
        return ResponseEntity.ok().body("Пользователь с id = " + id + " удален");
    }

    //Удаление текущего пользователя
    @DeleteMapping("/delete")
    public ResponseEntity<?> deleteCurrentUser(Principal principal){
        usersService.deleteByLogin(principal.getName());
        return ResponseEntity.ok(HttpStatus.OK);
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
