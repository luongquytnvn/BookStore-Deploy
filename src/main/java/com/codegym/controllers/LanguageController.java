package com.codegym.controllers;

import com.codegym.models.Language;
import com.codegym.services.impl.LanguageServiceImpl;
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
@RequestMapping("/api/language")
public class LanguageController {

    @Autowired
    private LanguageServiceImpl languageServiceImpl;
    @GetMapping("")
    public ResponseEntity<Iterable<Language>> showListLanguage() {
        Iterable<Language> languages = languageServiceImpl.findAllLanguage();
        return new ResponseEntity<Iterable<Language>>(languages, HttpStatus.OK);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity addNewLanguage(@Valid @RequestBody Language language) {
        try {
            languageServiceImpl.save(language);
            return new ResponseEntity(HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Language> getLanguageById(@PathVariable Long id) {
        Optional<Language> language = languageServiceImpl.findById(id);
        if (language.isPresent()) {
            return new ResponseEntity<>(language.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Language> updateLanguage(@PathVariable Long id, @RequestBody Language language) {
        Optional<Language> currentLanguage = languageServiceImpl.findById(id);
        if (currentLanguage.isPresent()) {
            currentLanguage.get().setId(id);
            currentLanguage.get().setBooks(language.getBooks());
            currentLanguage.get().setName(language.getName());

            languageServiceImpl.save(currentLanguage.get());
            return new ResponseEntity<Language>(currentLanguage.get(), HttpStatus.OK);
        }
        return new ResponseEntity<Language>(HttpStatus.NOT_FOUND);

    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Language> deleteLanguage(@PathVariable Long id) {
        Optional<Language> language = languageServiceImpl.findById(id);
        if (language.isPresent()) {
            languageServiceImpl.remote(id);
            return new ResponseEntity<Language>(HttpStatus.OK);
        }
        return new ResponseEntity<Language>(HttpStatus.NOT_FOUND);
    }
    @PostMapping("/findAllByName")
    public ResponseEntity<List<Language>> findAllByName(@RequestBody String name){
        List<Language> languageList = languageServiceImpl.findAllByNameContaining(name);
        if (!languageList.isEmpty()) {
            return new ResponseEntity<List<Language>>(languageList, HttpStatus.OK);
        }else {
            return new ResponseEntity<List<Language>>(HttpStatus.NOT_FOUND);
        }
    }
}
