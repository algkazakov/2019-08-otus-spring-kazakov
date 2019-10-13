package ru.otus.spring.dao;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.dto.BookDTO;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jdbc для работы с книгами ")
@JdbcTest
@Import({BookDaoJdbc.class})
class BookDaoJdbcTest {

    private static final int EXPECTED_NUMBER_OF_BOOKS = 4;

    @Autowired
    private BookDaoJdbc repositoryJdbc;

    @DisplayName("должен загружать список всех книг с полной информацией о них")
    @Test
    void shouldReturnCorrectBooksListWithAllInfo() {
        val books = repositoryJdbc.getAllFull();
        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS)
                .allMatch(b -> !b.getBook().getName().equals(""))
                .allMatch(b -> b.getAuthors() != null && b.getAuthors().size() > 0)
                .allMatch(b -> b.getGenres() != null && b.getGenres().size() > 0);
    }

    @DisplayName("должен загружать ровно 1 книгу")
    @Test
    void shouldReturnOneBook() {
        BookDTO bookDTO = repositoryJdbc.getById(4);
        assertThat(bookDTO).isNotNull()
                .matches(b -> !b.getBook().getName().equals(""))
                .matches(b -> b.getAuthors() != null && b.getAuthors().size() == 1)
                .matches(b -> b.getGenres() != null && b.getGenres().size() == 1);
    }

    @DisplayName("должен добавлять ровно 1 книгу c 2 авторами")
    @Test
    void shouldAddOneBook() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setBook(new Book(5, "Новая книга"));
        bookDTO.addAuthor(new Author(1, "Christian Bauer"));
        bookDTO.addAuthor(new Author(2, "Gavin King"));
        repositoryJdbc.insert(bookDTO);
        bookDTO = repositoryJdbc.getById(5);
        assertThat(bookDTO).isNotNull()
                .matches(b -> !b.getBook().getName().equals(""))
                .matches(b -> b.getAuthors() != null && b.getAuthors().size() == 2)
                .matches(b -> b.getGenres() != null && b.getGenres().isEmpty());
    }

    @DisplayName("должен изменять имя книги и ее автора")
    @Test
    void shouldEditBookNameAndAddAuthor() {
        BookDTO bookDTO = new BookDTO();
        bookDTO.setBook(new Book(4, "Совсем новая книга"));
        bookDTO.addAuthor(new Author(1, "Christian Bauer"));
        bookDTO.addAuthor(new Author(3, "Gary Gregory"));
        repositoryJdbc.update(bookDTO);
        bookDTO = repositoryJdbc.getById(4);
        assertThat(bookDTO).isNotNull()
                .matches(b -> b.getBook().getName().equals("Совсем новая книга"))
                .matches(b -> b.getAuthors() != null && b.getAuthors().size() == 2 && ((Author) b.getAuthors().toArray()[1]).getName().equals("Gary Gregory"));
    }


}