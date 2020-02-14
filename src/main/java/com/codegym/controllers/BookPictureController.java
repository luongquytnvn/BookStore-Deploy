package com.codegym.controllers;

import com.codegym.models.Author;
import com.codegym.models.BookPicture;
import com.codegym.services.BookPictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/book-picture")
public class BookPictureController {
    @Autowired
    private BookPictureService bookPictureService;

    @GetMapping("")
    public ResponseEntity<Iterable<BookPicture>> showListBookPicture() {
        Iterable<BookPicture> bookPictures = bookPictureService.findAllBookPicture();
        return new ResponseEntity<Iterable<BookPicture>>(bookPictures, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<?> addNewBookPicture(@RequestBody BookPicture bookPicture) {
        System.out.println("Creating Book picture ");
        bookPictureService.save(bookPicture);
        return new ResponseEntity<Long>(bookPicture.getId(), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookPicture> getBookPictureById(@PathVariable Long id) {

        Optional<BookPicture> bookPicture = bookPictureService.findById(id);
        if (bookPicture.isPresent()) {
            return new ResponseEntity<BookPicture>(bookPicture.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookPicture> updateBookPicture(@PathVariable Long id, @RequestBody BookPicture bookPicture) {
        Optional<BookPicture> currentBookPicture = bookPictureService.findById(id);
        if (currentBookPicture.isPresent()) {
            currentBookPicture.get().setId(id);
            currentBookPicture.get().setSrc(bookPicture.getSrc());

            bookPictureService.save(currentBookPicture.get());
            return new ResponseEntity<BookPicture>(currentBookPicture.get(), HttpStatus.OK);
        }
        return new ResponseEntity<BookPicture>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<BookPicture> deleteBookPicture(@PathVariable Long id) {
        Optional<BookPicture> bookPicture = bookPictureService.findById(id);
        if (bookPicture.isPresent()) {
            bookPictureService.remote(id);
            return new ResponseEntity<BookPicture>(HttpStatus.OK);
        }
        return new ResponseEntity<BookPicture>(HttpStatus.NOT_FOUND);
    }
}
