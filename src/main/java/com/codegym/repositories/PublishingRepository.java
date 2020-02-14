package com.codegym.repositories;

import com.codegym.models.Publishing;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublishingRepository extends CrudRepository<Publishing, Long> {
    List<Publishing>findAllByNameContaining(String name);
}
