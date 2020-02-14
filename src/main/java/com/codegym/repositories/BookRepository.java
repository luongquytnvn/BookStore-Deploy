package com.codegym.repositories;

import com.codegym.models.Book;
import com.codegym.models.Language;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface BookRepository extends JpaRepository<Book , Long> {
    List<Book> findByNameContainingOrderByDateCreateDesc(String name);
    List<Book> findAllByCategory_Id(Long id);
    List<Book> findAllByPublishing_Id(Long publishing_id);
    @Query(value = "select book_id from book join book_authors on book.id = book_authors.book_id where authors_id = ?1", nativeQuery = true)
    List<Long> findBookByAuthor(Long id);
    @Query(value = "select book.* from book join order_item on book.id = order_item.book_id join orders on order_item.order_id = orders.id where status='Done' group by book.id order by count(quantity) DESC", nativeQuery = true)
    List<Book> findBookByHot();
    @Query(value = "select * from book join book_languages bl on book.id = bl.book_id where languages_id = ?1", nativeQuery = true)
    List<Book> findBookByLanguages_Id(Long idLanguage);
    List<Book> findAllByNameContaining(String name);
}
