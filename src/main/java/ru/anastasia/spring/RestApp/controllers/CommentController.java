package ru.anastasia.spring.RestApp.controllers;

import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.anastasia.spring.RestApp.dto.CommentDTO;
import ru.anastasia.spring.RestApp.exception.comment.CommentErrorResponse;
import ru.anastasia.spring.RestApp.exception.comment.CommentNotCreatedException;
import ru.anastasia.spring.RestApp.exception.comment.CommentNotFoundException;
import ru.anastasia.spring.RestApp.models.Comment;
import ru.anastasia.spring.RestApp.services.CommentService;

import java.time.LocalDateTime;

@RestController
public class CommentController {

    private final CommentService commentService;
    private final ModelMapper modelMapper;

    public CommentController(CommentService commentService, ModelMapper modelMapper) {
        this.commentService = commentService;
        this.modelMapper = modelMapper;
    }

    private CommentDTO convertToCommentDTO (Comment comment) {
        return modelMapper.map(comment, CommentDTO.class);
    }

    private Comment convertToComment (CommentDTO commentDTO){
        return modelMapper.map(commentDTO, Comment.class);
    }

    @GetMapping("/comment/{id}")
    public CommentDTO getComment (@PathVariable("id") Long id){
        return convertToCommentDTO(commentService.getCommentById(id));
    }

    @DeleteMapping("/comment/{id}")
    public ResponseEntity<?> deleteComment(@PathVariable("id") Long id){
        commentService.deleteCommentById(id);
        return ResponseEntity.ok().body("Комментарий удален");
    }

    @PostMapping("/comment/create/{id}")
    public ResponseEntity<HttpStatus> createComment(@PathVariable("id") Long newsId,
                                                    @RequestBody @Valid CommentDTO commentDTO,
                                                    BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            StringBuilder builder = new StringBuilder();
            bindingResult.getFieldErrors().forEach(n ->
                    builder.append(n.getField())
                            .append(" - ")
                            .append(n.getDefaultMessage())
                            .append(";")
                    );
            throw new CommentNotCreatedException(builder.toString());
        }
        commentDTO.setDate(LocalDateTime.now());
        modelMapper.getConfiguration().setAmbiguityIgnored(true);
        commentService.saveComment(convertToComment(commentDTO),newsId);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/comment/{id}")
    public ResponseEntity<?> updateComment (@PathVariable("id") Long id,
                                            @RequestBody CommentDTO commentDTO){
        commentService.updateComment(id, commentDTO);
        return ResponseEntity.ok().body("Комментарий изменен");
    }

    @ExceptionHandler
    private ResponseEntity<CommentErrorResponse> handleException (CommentNotFoundException e){
        CommentErrorResponse commentErrorResponse = new CommentErrorResponse(
                "Комментарий с таким id не найден",
                LocalDateTime.now());
        return new ResponseEntity<>(commentErrorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    private ResponseEntity<CommentErrorResponse> handleException (CommentNotCreatedException e){
        CommentErrorResponse comment = new CommentErrorResponse(
                e.getMessage(),
                LocalDateTime.now());
        return new ResponseEntity<>(comment,HttpStatus.BAD_REQUEST);
    }
}
