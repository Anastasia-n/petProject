package ru.anastasia.spring.RestApp.services;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import ru.anastasia.spring.RestApp.dto.CommentDTO;
import ru.anastasia.spring.RestApp.exception.comment.CommentNotFoundException;
import ru.anastasia.spring.RestApp.exception.news.NewsNotFoundException;
import ru.anastasia.spring.RestApp.models.Comment;
import ru.anastasia.spring.RestApp.repositories.CommentRepository;
import ru.anastasia.spring.RestApp.repositories.NewsRepository;

@Service
@Transactional
public class CommentService {
    final CommentRepository commentRepository;
    final NewsRepository newsRepository;

    public CommentService(CommentRepository commentRepository, NewsRepository newsRepository) {
        this.commentRepository = commentRepository;
        this.newsRepository = newsRepository;
    }

    public Comment getCommentById (Long id){
        return commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
    }

    public void saveComment(Comment comment, Long newsId){
        comment.setNewsIdFk(newsRepository.findById(newsId).orElseThrow(NewsNotFoundException::new));
        comment.setUsersIdFK(null); // !!!!!
        commentRepository.save(comment);
    }

    public void deleteCommentById(Long id){
        if(commentRepository.existsById(id)){
            commentRepository.deleteById(id);
        } else {
            throw new CommentNotFoundException();
        }
    }

    public void updateComment(Long id, CommentDTO commentDTO){
        Comment comment = commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
        if(commentDTO.getText() != null && !commentDTO.getText().isBlank()){
            comment.setText(commentDTO.getText());
        }
        commentRepository.save(comment);
    }
}
