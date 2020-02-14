package com.codegym.services;

import com.codegym.models.Publishing;

import java.util.List;
import java.util.Optional;

public interface PublishingService {
    Iterable<Publishing> findAll();

    Optional<Publishing> findById(Long id);

    void save(Publishing publishing);

    void remove(Long id);
    List<Publishing>findAllByNameContaining(String name);
}
