package ru.anastasia.spring.RestApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anastasia.spring.RestApp.models.News;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    List<News> findByContentContains(String word);

    List<News> findByPublicationDateBetween(LocalDateTime date1, LocalDateTime date2);


}
