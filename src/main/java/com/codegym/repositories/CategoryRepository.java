package com.codegym.repositories;

import com.codegym.models.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository  extends CrudRepository<Category, Long>{
    List<Category> findAllByNameContaining(String name);
}
