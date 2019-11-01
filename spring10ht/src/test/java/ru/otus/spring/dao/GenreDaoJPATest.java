package ru.otus.spring.dao;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Genre;


import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе JPA для работы с жанрами ")
@DataJpaTest
class GenreDaoJPATest {

    private static final int EXPECTED_NUMBER_OF_GENRES = 3;

    @Autowired
    private GenreDao repositoryJPA;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен уметь считать количество всех жанров в БД")
    @Test
    void shouldReturnCountGenres() {
        val count = repositoryJPA.count();
        assertThat(count).isEqualTo(EXPECTED_NUMBER_OF_GENRES);
    }

    @DisplayName("должен загружать список всех жанров")
    @Test
    void shouldReturnCorrectGenresList() {
        val genres = repositoryJPA.findAll();
        assertThat(genres).isNotNull().hasSize(EXPECTED_NUMBER_OF_GENRES)
                .allMatch(g -> !g.getName().equals(""));
    }

    @DisplayName("должен загружать ровно 1 жанр")
    @Test
    void shouldReturnOneGenre() {
        Genre genre = repositoryJPA.findById(3L).get();
        assertThat(genre).isNotNull()
                .matches(g -> !g.getName().equals(""));
    }

    @DisplayName("должен добавлять ровно 1 жанр")
    @Test
    void shouldAddOneGenre() {
        Genre genre = new Genre(0, "Entertainment");
        repositoryJPA.save(genre);
        assertThat(genre.getId()).isGreaterThan(0);
        Genre genreFromDB = em.find(Genre.class, genre.getId());
        assertThat(genre).isEqualTo(genreFromDB);
    }

    @DisplayName("должен изменять имя жанра")
    @Test
    void shouldEditGenreName() {
        Genre genre = new Genre(3, "Test Driven development");
        repositoryJPA.save(genre);
        Genre genreFromDB = em.find(Genre.class, genre.getId());
        assertThat(genreFromDB).isNotNull()
                .matches(g -> g.getName().equals(genre.getName()));
    }

    @DisplayName("должен удалять жанр")
    @Test
    void shouldDeleteGenre() {
        repositoryJPA.deleteById(3L);
        Genre genreFromDB = em.find(Genre.class, 3L);
        assertThat(genreFromDB).isNull();
    }
}