package com.codegym.services.impl;

import com.codegym.models.Language;
import com.codegym.repositories.LanguageRepository;
import com.codegym.services.ILanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LanguageServiceImpl implements ILanguageService {
    @Autowired
    LanguageRepository languageRepository;
    @Override
    public Iterable<Language> findAllLanguage() {
        return languageRepository.findAll();
    }

    @Override
    public Optional<Language> findById(Long id) {
        return languageRepository.findById(id);
    }

    @Override
    public void save(Language language) {
        languageRepository.save(language);
    }

    @Override
    public void remote(Long id) {
        languageRepository.deleteById(id);
    }

    @Override
    public List<Language> findAllByNameContaining(String name) {
        return languageRepository.findAllByNameContaining(name);
    }
}
