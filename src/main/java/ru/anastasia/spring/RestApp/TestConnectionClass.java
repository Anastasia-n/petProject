package ru.anastasia.spring.RestApp;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.anastasia.spring.RestApp.dto.NewsDTO;
import ru.anastasia.spring.RestApp.dto.UsersDTO;
import ru.anastasia.spring.RestApp.models.News;
import ru.anastasia.spring.RestApp.models.Users;
import ru.anastasia.spring.RestApp.services.NewsService;
import ru.anastasia.spring.RestApp.services.UsersService;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class TestConnectionClass {

    private NewsService newsService;
    private final UsersService usersService;
    private final ModelMapper modelMapper;

    public TestConnectionClass(NewsService newsService, UsersService usersService, ModelMapper modelMapper) {
        this.newsService = newsService;
        this.usersService = usersService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/main/test")
    public String testConnection(){
        return "Hello, world!";
    }

    @GetMapping("/test/news")
    public List<NewsDTO> getNews () {
        return newsService.getAll().stream().map(this::convertToNewsDTO).collect(Collectors.toList());
       // convertToNewsDTO(newsService.getById(1L));
       // return newsService.getById(1L);
    }

    @PostMapping("/add")
    public ResponseEntity<HttpStatus> createUser (@RequestBody UsersDTO usersDTO){
        usersService.createUser(convertToUsers(usersDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    private Users convertToUsers(UsersDTO usersDTO){
        return modelMapper.map(usersDTO, Users.class);
    }

    private NewsDTO convertToNewsDTO (News news){
        return modelMapper.map(news, NewsDTO.class);
    }
}
