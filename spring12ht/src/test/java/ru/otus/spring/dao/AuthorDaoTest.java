package ru.otus.spring.dao;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.context.annotation.ComponentScan;
import ru.otus.spring.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе mongo для работы с авторами ")
@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.spring.config", "ru.otus.spring.dao"})
class AuthorDaoTest {

    private static final int EXPECTED_NUMBER_OF_AUTHORS = 6;

    @Autowired
    private AuthorDao repositoryJPA;

    @DisplayName("должен загружать список всех авторов")
    @Test
    void shouldReturnCorrectAuthorsList() {
        val authors = repositoryJPA.findAll();
        assertThat(authors).isNotNull()
                .allMatch(a -> !a.getName().equals(""));
    }

    @DisplayName("должен загружать ровно 1 автора")
    @Test
    void shouldReturnOneAuthor() {
        val authors = repositoryJPA.findAll();
        val firstAuthor = authors.get(0);
        Author author = repositoryJPA.findById(firstAuthor.getId()).get();
        assertThat(author).isNotNull()
                .matches(a -> !a.getName().equals(""));
    }

    @DisplayName("должен добавлять ровно 1 автора")
    @Test
    void shouldAddOneAuthor() {
        Author author = new Author("Agata Cristy");
        Author authorFromDB = repositoryJPA.save(author);
        assertThat(author.getId()).isNotEmpty();
        assertThat(author).isEqualTo(authorFromDB);
    }

    @DisplayName("должен изменять имя автора")
    @Test
    void shouldEditAuthorName() {
        val authors = repositoryJPA.findAll();
        val author = authors.get(0);
        author.setName("Alexander Pushkin");
        Author authorFromDB = repositoryJPA.save(author);
        assertThat(authorFromDB).isNotNull()
                .matches(a -> a.getName().equals(author.getName()));
    }

    @DisplayName("должен удалять автора")
    @Test
    void shouldDeleteAuthor() {
        val authors = repositoryJPA.findAll();
        val firstAuthor = authors.get(0);
        repositoryJPA.deleteById(firstAuthor.getId());
        val author = repositoryJPA.findById(firstAuthor.getId());
        assertThat(author).isEmpty();
    }
}