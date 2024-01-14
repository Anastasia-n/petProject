package ru.anastasia.spring.RestApp.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.anastasia.spring.RestApp.dto.UsersDTO;
import ru.anastasia.spring.RestApp.exception.user.UserErrorResponse;
import ru.anastasia.spring.RestApp.exception.user.UserNotCreatedException;
import ru.anastasia.spring.RestApp.models.Users;
import ru.anastasia.spring.RestApp.services.UsersService;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UsersService usersService;
    private final ModelMapper modelMapper;

    public AuthController(UsersService usersService, ModelMapper modelMapper) {
        this.usersService = usersService;
        this.modelMapper = modelMapper;
    }

    private Users convertToUsers (UsersDTO usersDTO){
        return modelMapper.map(usersDTO, Users.class);
    }

    //Регистрация пользователя
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid UsersDTO usersDTO,
                                      BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder stringBuilder = new StringBuilder();
            bindingResult.getFieldErrors().forEach(n -> stringBuilder
                    .append(n.getField())
                    .append(" - ")
                    .append(n.getDefaultMessage())
                    .append(";"));
            throw new UserNotCreatedException(stringBuilder.toString());
        }
        usersService.createUser(convertToUsers(usersDTO));
        return ResponseEntity.ok().body("Пользователь зарегистрирован");
    }

    @ExceptionHandler
    private ResponseEntity<UserErrorResponse> handleException (UserNotCreatedException e){
        UserErrorResponse response = new UserErrorResponse(
                e.getMessage(),
                LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

}