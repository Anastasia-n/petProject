package ru.anastasia.spring.RestApp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.anastasia.spring.RestApp.models.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
