package ru.otus.spring.dao;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Genre;


import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе Jdbc для работы с жанрами ")
@JdbcTest
@Import({GenreDaoJdbc.class})
class GenreDaoJdbcTest {

    private static final int EXPECTED_NUMBER_OF_GENRES = 3;

    @Autowired
    private GenreDaoJdbc repositoryJdbc;

    @DisplayName("должен уметь считать количество всех жанров в БД")
    @Test
    void shouldReturnCountAuthors() {
        val count = repositoryJdbc.count();
        assertThat(count).isEqualTo(EXPECTED_NUMBER_OF_GENRES);
    }

    @DisplayName("должен загружать список всех жанров")
    @Test
    void shouldReturnCorrectAuthorsList() {
        val genres = repositoryJdbc.getAll();
        assertThat(genres).isNotNull().hasSize(EXPECTED_NUMBER_OF_GENRES)
                .allMatch(g -> !g.getName().equals(""));
    }

    @DisplayName("должен загружать ровно 1 жанр")
    @Test
    void shouldReturnOneAuthor() {
        Genre genre = repositoryJdbc.getById(3);
        assertThat(genre).isNotNull()
                .matches(g -> !g.getName().equals(""));
    }

    @DisplayName("должен добавлять ровно 1 жанр")
    @Test
    void shouldAddOneAuthor() {
        long nextGenreId = repositoryJdbc.getNextId();
        Genre genre = new Genre(nextGenreId, "Entertainment");
        repositoryJdbc.insert(genre);
        Genre genreFromDB = repositoryJdbc.getById(nextGenreId);
        assertThat(genre).isEqualTo(genreFromDB);
    }

    @DisplayName("должен изменять имя жанра")
    @Test
    void shouldEditAuthorName() {
        Genre genre = new Genre(3, "Test Driven development");
        repositoryJdbc.update(genre);
        genre = repositoryJdbc.getById(3);
        assertThat(genre).isNotNull()
                .matches(g -> g.getName().equals("Test Driven development"));
    }

    @DisplayName("должен удалять жанр")
    @Test
    void shouldDeleteBookNameAndAddAuthor() {
        repositoryJdbc.deleteById(3);
        Genre genre = repositoryJdbc.getById(3);
        assertThat(genre).isNull();
    }
}