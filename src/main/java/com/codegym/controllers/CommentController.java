package com.codegym.controllers;

import com.codegym.models.Comment;
import com.codegym.services.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("")
    public ResponseEntity<List<Comment>> listAllComments() {
        List<Comment> comments = (List<Comment>) commentService.findAll();
        if (comments.isEmpty()) {
            return new ResponseEntity<List<Comment>>(comments, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<List<Comment>> findAllByBook_Id(@PathVariable Long id) {
        List<Comment> comments = (List<Comment>) commentService.findAllByBook_Id(id);
        if (comments.isEmpty()) {
            return new ResponseEntity<List<Comment>>(comments, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Comment>>(comments, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Comment>> getComment(@PathVariable("id") long id) {
        System.out.println("Comment with id " + id);
        Optional<Comment> comment = commentService.findById(id);
        if (!comment.isPresent()) {
            System.out.println("Comment with id " + id + "not found");
            return new ResponseEntity<Optional<Comment>>(comment, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Optional<Comment>>(comment, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> createComment(@RequestBody Comment comment) {
        long millis = System.currentTimeMillis();
        java.sql.Date date = new java.sql.Date(millis);
        comment.setDate(date);
        try {
            commentService.save(comment);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception ex) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Optional<Comment>> updateComment(@PathVariable("id") long id, @RequestBody Comment comment) {
        System.out.println("Update Comment " + id);

        Optional<Comment> currentComment = commentService.findById(id);

        if (currentComment.isPresent()) {
            currentComment.get().setEdit(true);
            currentComment.get().setContent(comment.getContent());
            commentService.save(currentComment.get()
            );
            return new ResponseEntity<Optional<Comment>>(currentComment, HttpStatus.OK);
        }
        System.out.println("Comment with id" + id + "not found");
        return new ResponseEntity<Optional<Comment>>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Comment> deleteComment(@PathVariable("id") long id) {
        System.out.println("Delete Comment with id" + id);

        Optional<Comment> comment = commentService.findById(id);
        if (!comment.isPresent()) {
            System.out.println("Unable to delete. Comment with id" + id + "not found");
            return new ResponseEntity<Comment>(HttpStatus.NOT_FOUND);
        }
        commentService.remove(id);
        return new ResponseEntity<Comment>(HttpStatus.NO_CONTENT);
    }
}
