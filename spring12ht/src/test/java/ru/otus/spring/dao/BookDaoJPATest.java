package ru.otus.spring.dao;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе JPA для работы с книгами ")
@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.spring.config", "ru.otus.spring.dao"})
class BookDaoJPATest {

    @Autowired
    private BookDao repositoryJPA;
    @Autowired
    private AuthorDao repositoryAuthorJPA;


    @DisplayName("должен загружать список всех книг с полной информацией о них")
    @Test
    void shouldReturnCorrectBooksListWithAllInfo() {
        val books = repositoryJPA.findAll();
        assertThat(books).isNotNull()
                .allMatch(b -> !b.getName().equals(""))
                .allMatch(b -> b.getAuthors() != null && b.getAuthors().size() > 0)
                .allMatch(b -> b.getGenres() != null);
    }

    @DisplayName("должен загружать ровно 1 книгу")
    @Test
    void shouldReturnOneBook() {
        val books = repositoryJPA.findAll();
        Book book = books.get(0);
        assertThat(book).isNotNull()
                .matches(b -> !b.getName().equals(""))
                .matches(b -> b.getAuthors() != null && b.getAuthors().size() == 3)
                .matches(b -> b.getGenres() != null && b.getGenres().size() == 1);
    }

    @DisplayName("должен добавлять ровно 1 книгу c 2 авторами")
    @Test
    void shouldAddOneBook() {
        val authors = repositoryAuthorJPA.findAll();
        Book book = new Book("Новая книга", Arrays.asList(new Author [] {authors.get(0), authors.get(1)}));
        Book bookEM = repositoryJPA.save(book);
        assertThat(book.getId()).isNotEmpty();
        assertThat(bookEM).isNotNull()
                .matches(b -> b.getName().equals(book.getName()))
                .matches(b -> b.getAuthors() != null && b.getAuthors().size() == 2)
                .matches(b -> b.getGenres() != null && b.getGenres().isEmpty());
    }

    @DisplayName("должен изменять имя книги и ее автора")
    @Test
    void shouldEditBookNameAndAddAuthor() {
        val books = repositoryJPA.findAll();
        Book book = books.get(0);
        book.setName("Совсем новая книга");
        val authors = repositoryAuthorJPA.findAll();
        book.getAuthors().add(authors.get(5));
        Book bookDB = repositoryJPA.save(book);
        assertThat(bookDB).isNotNull()
                .matches(b -> b.getName().equals("Совсем новая книга"), "название")
                .matches(b -> b.getAuthors() != null && b.getAuthors().size() == 2, "количество авторов 2");
    }

    @DisplayName("должен удалять книгу")
    @Test
    void shouldDeleteBook() {
        val books = repositoryJPA.findAll();
        Book book = books.get(0);
        repositoryJPA.deleteById(book.getId());
        val oldBook = repositoryJPA.findById(book.getId());
        assertThat(oldBook).isEmpty();
    }


}