package ru.anastasia.spring.RestApp.exception.news;

public class NewsNotCreatedException extends RuntimeException{
    public NewsNotCreatedException(String message){
        super(message);
    }
}
