package com.codegym.services;

import com.codegym.models.BookPicture;

import java.util.Optional;

public interface BookPictureService {
    Iterable<BookPicture> findAllBookPicture();
    Optional<BookPicture> findById(Long id);
    void save(BookPicture bookPicture);
    void remote(Long id);
}
