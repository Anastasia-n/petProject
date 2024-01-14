package ru.anastasia.spring.RestApp.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.anastasia.spring.RestApp.dto.NewsDTO;
import ru.anastasia.spring.RestApp.exception.user.UserNotFoundException;
import ru.anastasia.spring.RestApp.models.News;
import ru.anastasia.spring.RestApp.models.Users;
import ru.anastasia.spring.RestApp.repositories.NewsRepository;
import ru.anastasia.spring.RestApp.exception.news.NewsNotFoundException;
import ru.anastasia.spring.RestApp.repositories.UsersRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class NewsService {

    final NewsRepository newsRepository;
    final UsersRepository usersRepository;

    public NewsService(NewsRepository newsRepository, UsersRepository usersRepository) {
        this.newsRepository = newsRepository;
        this.usersRepository = usersRepository;
    }

    public List<News> getAll(){
        return newsRepository.findAll();
    }

    public News getById (Long id) {
        return newsRepository.findById(id).orElseThrow(NewsNotFoundException::new);
    }

    public List<News> getByWord(String word){
        return newsRepository.findByContentContains(word);
    }

    public List<News> getByDate(LocalDateTime dateTime1, LocalDateTime dateTime2){
        return newsRepository.findByPublicationDateBetween(dateTime1, dateTime2);
    }

    public void saveNews(News news, String login) {
        Users users = usersRepository.findByLogin(login).get();
        news.setUserIdFK(users);
        newsRepository.save(news);
    }

    public void deleteNews(Long id){
        if(newsRepository.existsById(id)){
            newsRepository.deleteById(id);
        } else {
            throw new NewsNotFoundException();
        }
    }

    public void updateNews(Long id, NewsDTO newsDTO){
        News news = newsRepository.findById(id).orElseThrow(NewsNotFoundException::new);
        if(newsDTO.getTitle() != null && !newsDTO.getTitle().isBlank()){ //true if the string is empty or contains only white space codepoints
            news.setTitle(newsDTO.getTitle());
        }
        if(newsDTO.getContent() != null && !newsDTO.getContent().isBlank()){
            news.setContent(newsDTO.getContent());
        }
        newsRepository.save(news);
    }
}
