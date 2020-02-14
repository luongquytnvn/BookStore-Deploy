package com.codegym.services.impl;

import com.codegym.models.Publishing;
import com.codegym.repositories.PublishingRepository;
import com.codegym.services.PublishingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PublishingServiceImpl implements PublishingService {

    @Autowired
    private PublishingRepository publishingRepository;

    @Override
    public Iterable<Publishing> findAll() {
        return publishingRepository.findAll();
    }

    @Override
    public Optional<Publishing> findById(Long id) {
        return publishingRepository.findById(id);
    }

    @Override
    public void save(Publishing publishing) {
        publishingRepository.save(publishing);
    }

    @Override
    public void remove(Long id) {
        publishingRepository.deleteById(id);
    }

    @Override
    public List<Publishing> findAllByNameContaining(String name) {
        return publishingRepository.findAllByNameContaining(name);
    }
}
