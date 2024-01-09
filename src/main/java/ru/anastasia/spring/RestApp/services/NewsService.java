package ru.anastasia.spring.RestApp.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.anastasia.spring.RestApp.dto.NewsDTO;
import ru.anastasia.spring.RestApp.models.News;
import ru.anastasia.spring.RestApp.repositories.NewsRepository;
import ru.anastasia.spring.RestApp.exception.news.NewsNotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class NewsService {

    final NewsRepository newsRepository;

    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
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

    public void saveNews(News news) {
        news.setUserIdFK(null); //!!!
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
