package ru.otus.spring.dao;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import ru.otus.spring.domain.Book;

import java.util.Optional;

public interface BookDao extends CrudRepository<Book, Long> {
    @Override
    @Query("select distinct b from Book b join fetch b.authors join fetch b.genres order by b.id")
    Iterable<Book> findAll();

    @Override
    @Query("select b from Book b join fetch b.authors join fetch b.genres where b.id = ?1")
    Optional<Book> findById(Long id);
}
