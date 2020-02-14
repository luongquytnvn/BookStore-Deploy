package com.codegym.controllers;

import com.codegym.models.Author;
import com.codegym.models.Category;
import com.codegym.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/api/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("")
    public ResponseEntity<List<Category>> listAllCategories() {
        List<Category> categories = (List<Category>) categoryService.findAll();
        if (categories.isEmpty()) {
            return new ResponseEntity<List<Category>>(categories, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<List<Category>>(categories, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<Category>> getCategory(@PathVariable("id") long id) {
        System.out.println("Category with id " + id);
        Optional<Category> category = categoryService.findById(id);
        if (!category.isPresent()) {
            System.out.println("Category with id " + id + "not found");
            return new ResponseEntity<Optional<Category>>(category, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<Optional<Category>>(category, HttpStatus.OK);
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> createCategory(@RequestBody Category category, UriComponentsBuilder uriComponentsBuilder) {
        System.out.println("Create Category" + category.getName());
        categoryService.save(category);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uriComponentsBuilder.path("/categories/{id}").buildAndExpand(category.getId()).toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Optional<Category>> updateCategory(@PathVariable("id") long id, @RequestBody Category category) {
        System.out.println("Update Category" + id);

        Optional<Category> currentCategory = categoryService.findById(id);

        if (!currentCategory.isPresent()) {
            System.out.println("Category with id" + id + "not found");
            return new ResponseEntity<Optional<Category>>(HttpStatus.NOT_FOUND);
        }

        currentCategory.get().setName(category.getName());
        currentCategory.get().setBooks(category.getBooks());
        categoryService.save(category);
        return new ResponseEntity<Optional<Category>>(currentCategory, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Category> deleteCategory(@PathVariable("id") long id) {
        System.out.println("Delete Category with id" + id);

        Optional<Category> category = categoryService.findById(id);
        if (!category.isPresent()) {
            System.out.println("Unable to delete. Category with id" + id + "not found");
            return new ResponseEntity<Category>(HttpStatus.NOT_FOUND);
        }
        categoryService.remove(id);
        return new ResponseEntity<Category>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/findAllByName")
    public ResponseEntity<List<Category>> findAllByName(@RequestBody String name){
        List<Category> categoryList = categoryService.findAllByNameContaining(name);
        if (!categoryList.isEmpty()) {
            return new ResponseEntity<List<Category>>(categoryList, HttpStatus.OK);
        }else {
            return new ResponseEntity<List<Category>>(HttpStatus.NOT_FOUND);
        }
    }
}
