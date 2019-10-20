package ru.otus.spring.dao;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Author;


import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jdbc для работы с авторами ")
@JdbcTest
@Import({AuthorDaoJdbc.class})
class AuthorDaoJdbcTest {

    private static final int EXPECTED_NUMBER_OF_AUTHORS = 6;

    @Autowired
    private AuthorDaoJdbc repositoryJdbc;

    @DisplayName("должен уметь считать количество всех авторов в БД")
    @Test
    void shouldReturnCountAuthors() {
        val count = repositoryJdbc.count();
        assertThat(count).isEqualTo(EXPECTED_NUMBER_OF_AUTHORS);
    }

    @DisplayName("должен загружать список всех авторов")
    @Test
    void shouldReturnCorrectAuthorsList() {
        val authors = repositoryJdbc.getAll();
        assertThat(authors).isNotNull().hasSize(EXPECTED_NUMBER_OF_AUTHORS)
                .allMatch(a -> !a.getName().equals(""));
    }

    @DisplayName("должен загружать ровно 1 автора")
    @Test
    void shouldReturnOneAuthor() {
        Author author = repositoryJdbc.getById(6);
        assertThat(author).isNotNull()
                .matches(a -> !a.getName().equals(""));
    }

    @DisplayName("должен добавлять ровно 1 автора")
    @Test
    void shouldAddOneAuthor() {
        Author author = new Author(7, "Agata Cristy");
        repositoryJdbc.insert(author);
        Author authorFromDB = repositoryJdbc.getById(7);
        assertThat(author).isEqualTo(authorFromDB);
    }

    @DisplayName("должен изменять имя автора")
    @Test
    void shouldEditAuthorName() {
        Author author = new Author(6, "Alexander Pushkin");
        repositoryJdbc.update(author);
        author = repositoryJdbc.getById(6);
        assertThat(author).isNotNull()
                .matches(a -> a.getName().equals("Alexander Pushkin"));
    }

    @DisplayName("должен удалять автора")
    @Test
    void shouldDeleteAuthor() {
        repositoryJdbc.deleteById(6);
        Author author = repositoryJdbc.getById(6);
        assertThat(author).isNull();
    }


}