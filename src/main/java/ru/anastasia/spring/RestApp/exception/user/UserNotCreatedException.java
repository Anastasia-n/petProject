package ru.anastasia.spring.RestApp.exception.user;

public class UserNotCreatedException extends RuntimeException{
    public UserNotCreatedException(String message){
        super(message);
    }
}
