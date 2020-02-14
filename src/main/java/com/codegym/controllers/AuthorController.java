package com.codegym.controllers;

import com.codegym.models.Author;
import com.codegym.models.Category;
import com.codegym.services.IAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/author")
public class AuthorController {
    @Autowired
    private IAuthorService authorService;

    @GetMapping("")
    public ResponseEntity<Iterable<Author>> showListAuthor() {
        Iterable<Author> authors = authorService.findAllAuthor();
        return new ResponseEntity<Iterable<Author>>(authors, HttpStatus.OK);
    }
    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> addNewAuthor(@Valid @RequestBody Author author){
        try {
            authorService.save(author);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id){
        Optional<Author> author = authorService.findById(id);
        if (author.isPresent()){
            System.out.println("find Author");
            return new ResponseEntity<Author>(author.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody Author author){
        Optional<Author> currentAuthor = authorService.findById(id);
        if (currentAuthor.isPresent()){
            currentAuthor.get().setId(id);
            currentAuthor.get().setBooks(author.getBooks());
            currentAuthor.get().setCountry(author.getCountry());
            currentAuthor.get().setInFor(author.getInFor());
            currentAuthor.get().setName(author.getName());

            authorService.save(currentAuthor.get());
            return new ResponseEntity<Author>(currentAuthor.get(), HttpStatus.OK);
        }
        return new ResponseEntity<Author>(HttpStatus.NOT_FOUND);

    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Author> deleteAuthor(@PathVariable Long id){
        Optional<Author> author = authorService.findById(id);
        if (author.isPresent()){
            authorService.remote(id);
            return new ResponseEntity<Author>(HttpStatus.OK);
        }
        return new ResponseEntity<Author>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/findAllByName")
    public ResponseEntity<List<Author>> findAllByName(@RequestBody String name){
        List<Author> authorList = authorService.findAllByNameContaining(name);
        if (!authorList.isEmpty()) {
            return new ResponseEntity<List<Author>>(authorList, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
