package com.codegym.services.impl;

import com.codegym.models.Author;
import com.codegym.repositories.AuthorRepository;
import com.codegym.services.IAuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AuthorServiceImpl implements IAuthorService {
    @Autowired
    AuthorRepository authorRepository;

    @Override
    public Iterable<Author> findAllAuthor() {
        return authorRepository.findAll();
    }

    @Override
    public Optional<Author> findById(Long id) {
        return authorRepository.findById(id);
    }

    @Override
    public void save(Author author) {
        authorRepository.save(author);
    }

    @Override
    public void remote(Long id) {
    authorRepository.deleteById(id);
    }

    @Override
    public List<Author> findAllByNameContaining(String name) {
        return authorRepository.findAllByNameContaining(name);
    }
}
