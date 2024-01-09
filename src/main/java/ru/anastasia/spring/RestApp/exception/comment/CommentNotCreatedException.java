package ru.anastasia.spring.RestApp.exception.comment;

public class CommentNotCreatedException extends RuntimeException{
    public CommentNotCreatedException(String message){
        super(message);
    }
}
