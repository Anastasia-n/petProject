package ru.anastasia.spring.RestApp.exception.user;

public class UserNotUpdatedException extends RuntimeException{
    public UserNotUpdatedException(String message){
        super(message);
    }
}
