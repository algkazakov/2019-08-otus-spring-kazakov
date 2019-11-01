package ru.otus.spring.dao;

import lombok.val;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import ru.otus.spring.domain.Author;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе JPA для работы с авторами ")
@DataJpaTest
class AuthorDaoJPATest {

    private static final int EXPECTED_NUMBER_OF_AUTHORS = 6;

    @Autowired
    private AuthorDao repositoryJPA;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен уметь считать количество всех авторов в БД")
    @Test
    void shouldReturnCountAuthors() {
        val count = repositoryJPA.count();
        assertThat(count).isEqualTo(EXPECTED_NUMBER_OF_AUTHORS);
    }

    @DisplayName("должен загружать список всех авторов")
    @Test
    void shouldReturnCorrectAuthorsList() {
        val authors = repositoryJPA.findAll();
        assertThat(authors).isNotNull().hasSize(EXPECTED_NUMBER_OF_AUTHORS)
                .allMatch(a -> !a.getName().equals(""));
    }

    @DisplayName("должен загружать ровно 1 автора")
    @Test
    void shouldReturnOneAuthor() {
        Author author = repositoryJPA.findById(6L).get();
        assertThat(author).isNotNull()
                .matches(a -> !a.getName().equals(""));
    }

    @DisplayName("должен добавлять ровно 1 автора")
    @Test
    void shouldAddOneAuthor() {
        Author author = new Author(0, "Agata Cristy");
        repositoryJPA.save(author);
        assertThat(author.getId()).isGreaterThan(0);
        Author authorFromDB = em.find(Author.class, author.getId());
        assertThat(author).isEqualTo(authorFromDB);
    }

    @DisplayName("должен изменять имя автора")
    @Test
    void shouldEditAuthorName() {
        Author author = new Author(6, "Alexander Pushkin");
        repositoryJPA.save(author);
        Author authorFromDB = em.find(Author.class, author.getId());
        assertThat(authorFromDB).isNotNull()
                .matches(a -> a.getName().equals(author.getName()));
    }

    @DisplayName("должен удалять автора")
    @Test
    void shouldDeleteAuthor() {
        repositoryJPA.deleteById(6L);
        Author author = em.find(Author.class, 6L);
        assertThat(author).isNull();
    }
}