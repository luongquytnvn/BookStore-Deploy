package com.codegym.models;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
public class Author {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 4, max = 50)
    private String name;

    @NotBlank
    @Size(min = 4)
    private String inFor;

    @NotBlank
    @Size(min = 4)
    private String country;

    @ManyToMany
    private Collection<Book> books;
    public Author() {
    }

    public Author(Long id, String name, String inFor, String country, Collection<Book> books) {
        this.id = id;
        this.name = name;
        this.inFor = inFor;
        this.country = country;
        this.books = books;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInFor() {
        return inFor;
    }

    public void setInFor(String inFor) {
        this.inFor = inFor;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Collection<Book> getBooks() {
        return books;
    }

    public void setBooks(Collection<Book> books) {
        this.books = books;
    }
}
