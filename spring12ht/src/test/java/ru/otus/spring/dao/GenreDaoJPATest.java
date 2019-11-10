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
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Genre;


import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе JPA для работы с жанрами ")
@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.spring.config", "ru.otus.spring.dao"})
class GenreDaoJPATest {

    @Autowired
    private GenreDao repositoryJPA;

    @DisplayName("должен загружать список всех жанров")
    @Test
    void shouldReturnCorrectGenresList() {
        val genres = repositoryJPA.findAll();
        assertThat(genres).isNotNull()
                .allMatch(g -> !g.getName().equals(""));
    }

    @DisplayName("должен загружать ровно 1 жанр")
    @Test
    void shouldReturnOneGenre() {
        val genres = repositoryJPA.findAll();
        val genre = genres.get(0);
        Genre genreDB = repositoryJPA.findById(genre.getId()).get();
        assertThat(genreDB).isNotNull()
                .matches(g -> !g.getName().equals(""));
    }

    @DisplayName("должен добавлять ровно 1 жанр")
    @Test
    void shouldAddOneGenre() {
        Genre genre = new Genre("Entertainment");
        Genre genreFromDB = repositoryJPA.save(genre);
        assertThat(genre.getId()).isNotEmpty();
        assertThat(genre).isEqualTo(genreFromDB);
    }

    @DisplayName("должен изменять имя жанра")
    @Test
    void shouldEditGenreName() {
        val genres = repositoryJPA.findAll();
        val genre = genres.get(0);
        genre.setName( "Test Driven development");
        Genre genreFromDB = repositoryJPA.save(genre);
        assertThat(genreFromDB).isNotNull()
                .matches(g -> g.getName().equals(genre.getName()));
    }

    @DisplayName("должен удалять жанр")
    @Test
    void shouldDeleteGenre() {
        val genres = repositoryJPA.findAll();
        val genre = genres.get(0);
        repositoryJPA.deleteById(genre.getId());
        val genreFromDB = repositoryJPA.findById(genre.getId());
        assertThat(genreFromDB).isEmpty();
    }
}