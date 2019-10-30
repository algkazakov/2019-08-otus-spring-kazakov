package ru.otus.spring.dao;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе JPA для работы с книгами ")
@DataJpaTest
class BookDaoJPATest {

    private static final int EXPECTED_NUMBER_OF_BOOKS = 4;

    @Autowired
    private BookDao repositoryJPA;
    @Autowired
    private AuthorDao repositoryAuthorJPA;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен уметь считать количество всех книг в БД")
    @Test
    void shouldReturnCountBooks() {
        val count = repositoryJPA.count();
        assertThat(count).isEqualTo(EXPECTED_NUMBER_OF_BOOKS);
    }

    @DisplayName("должен загружать список всех книг с полной информацией о них")
    @Test
    void shouldReturnCorrectBooksListWithAllInfo() {
        val books = repositoryJPA.findAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS)
                .allMatch(b -> !b.getName().equals(""))
                .allMatch(b -> b.getAuthors() != null && b.getAuthors().size() > 0)
                .allMatch(b -> b.getGenres() != null && b.getGenres().size() > 0);
    }

    @DisplayName("должен загружать список всех книг без полной информации о них")
    @Test
    void shouldReturnCorrectBooksListWithoutAllInfo() {
        val books = repositoryJPA.findAll();
        assertThat(books).isNotNull().hasSize(EXPECTED_NUMBER_OF_BOOKS)
                .allMatch(b -> !b.getName().equals(""));
    }

    @DisplayName("должен загружать ровно 1 книгу")
    @Test
    void shouldReturnOneBook() {
        Book book = repositoryJPA.findById(1L).get();
        assertThat(book).isNotNull()
                .matches(b -> !b.getName().equals(""))
                .matches(b -> b.getAuthors() != null && b.getAuthors().size() == 3)
                .matches(b -> b.getGenres() != null && b.getGenres().size() == 1);
    }

    @DisplayName("должен добавлять ровно 1 книгу c 2 авторами")
    @Test
    void shouldAddOneBook() {
        Book book = new Book(0, "Новая книга");
        book.getAuthors().add(new Author(1, "Christian Bauer"));
        book.getAuthors().add(new Author(2, "Gavin King"));
        repositoryJPA.save(book);
        assertThat(book.getId()).isGreaterThan(0);
        Book bookEM = em.find(Book.class, book.getId());
        assertThat(bookEM).isNotNull()
                .matches(b -> b.getName().equals(book.getName()))
                .matches(b -> b.getAuthors() != null && b.getAuthors().size() == 2)
                .matches(b -> b.getGenres() != null && b.getGenres().isEmpty());
    }

    @DisplayName("должен изменять имя книги и ее автора")
    @Test
    void shouldEditBookNameAndAddAuthor() {
        Book book = repositoryJPA.findById(3L).get();
        book.setName("Совсем новая книга");
        book.getAuthors().add(repositoryAuthorJPA.findById(3L).get());
        book.getAuthors().add(repositoryAuthorJPA.findById(4L).get());
        repositoryJPA.save(book);
        book = repositoryJPA.findById(3L).get();
        assertThat(book).isNotNull()
                .matches(b -> b.getName().equals("Совсем новая книга"))
                .matches(b -> b.getAuthors() != null && b.getAuthors().size() == 4);
    }

    @DisplayName("должен удалять книгу")
    @Test
    void shouldDeleteBook() {
        repositoryJPA.deleteById(1L);
        Book book = em.find(Book.class, 1L);
        assertThat(book).isNull();
    }


}