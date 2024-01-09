package ru.anastasia.spring.RestApp.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import ru.anastasia.spring.RestApp.dto.CommentDTO;
import ru.anastasia.spring.RestApp.dto.NewsDTO;
import ru.anastasia.spring.RestApp.models.News;
import ru.anastasia.spring.RestApp.services.NewsService;
import ru.anastasia.spring.RestApp.exception.news.NewsErrorResponse;
import ru.anastasia.spring.RestApp.exception.news.NewsNotCreatedException;
import ru.anastasia.spring.RestApp.exception.news.NewsNotFoundException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class NewsController {

    private final NewsService newsService;
    private final ModelMapper modelMapper;

    public NewsController(NewsService newsService, ModelMapper modelMapper) {
        this.newsService = newsService;
        this.modelMapper = modelMapper;
    }

    private NewsDTO convertToNewsDTO(News news){
        return modelMapper.map(news, NewsDTO.class);
    }

    private News convertToNews (NewsDTO newsDTO){
        return modelMapper.map(newsDTO, News.class);
    }

    @GetMapping("/news")
    public List<NewsDTO> getAllNews() {
        return newsService.getAll().stream().map(this::convertToNewsDTO).collect(Collectors.toList());
    }

    @GetMapping("/news/{id}")
    public NewsDTO getNewsById(@PathVariable("id") Long id) {
        return convertToNewsDTO(newsService.getById(id));
    }

    @GetMapping("/news/find")
    public List<NewsDTO> getNewsByKey(@RequestParam(value = "keyword") String keyword){
        return newsService.getByWord(keyword).stream().map(this::convertToNewsDTO).collect(Collectors.toList());
    }

    @GetMapping("/news/date")
    public List<NewsDTO> getNewsByDate (@RequestParam("fromDate") String date1,
                                       @RequestParam("toDate") String date2) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return newsService.getByDate(LocalDateTime.parse(date1,formatter), LocalDateTime.parse(date2,formatter))
                .stream().map(this::convertToNewsDTO).collect(Collectors.toList());
    }

    @PostMapping("/news/create")
    public ResponseEntity<HttpStatus> createNews(@RequestBody @Valid NewsDTO newsDTO,
                                                 BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder stringBuilder = new StringBuilder();
            List<FieldError> errors = bindingResult.getFieldErrors();
            for(FieldError error : errors){
                stringBuilder.append(error.getField()).append(" - ").append(error.getDefaultMessage()).append(";");
            }
            throw new NewsNotCreatedException(stringBuilder.toString());
        }
        newsDTO.setPublicationDate(LocalDateTime.now());
        newsService.saveNews(convertToNews(newsDTO));
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @DeleteMapping("/news/{id}")
    public ResponseEntity<?> deleteNews(@PathVariable("id") Long id){
        newsService.deleteNews(id);
        return ResponseEntity.ok().body("Новость с id = " + id + " удалена");
    }

    @GetMapping("/news/comments/{id}")
    public List<CommentDTO> getCommentsByNewsId(@PathVariable("id") Long id) {
        return newsService.getById(id).getCommentList()
                .stream()
                .map(n -> modelMapper.map(n, CommentDTO.class))
                .collect(Collectors.toList());
    }

    @PatchMapping("/news/{id}")
    public ResponseEntity<?> updateNews (@PathVariable("id") Long id,
                                         @RequestBody NewsDTO newsDTO){
        newsService.updateNews(id, newsDTO);
        return ResponseEntity.ok().body("Новость с id = " + id + " изменена");
    }


    @ExceptionHandler
    private ResponseEntity<NewsErrorResponse> handleException(NewsNotFoundException e) {
        NewsErrorResponse newsErrorResponse = new NewsErrorResponse(
                "Новость с таким id не найдена",
                LocalDateTime.now());
        return new ResponseEntity<>(newsErrorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<NewsErrorResponse> handleException(NewsNotCreatedException e) {
        NewsErrorResponse newsErrorResponse = new NewsErrorResponse(
                e.getMessage(),
                LocalDateTime.now());
        return new ResponseEntity<>(newsErrorResponse,HttpStatus.BAD_REQUEST);
    }

}
