package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Comment;


import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе JPA для работы с комментариями к книгам ")
@DataJpaTest
@Import({CommentDaoJPA.class})
class CommentDaoJPATest {

    @Autowired
    private CommentDaoJPA repositoryJPA;

    @Autowired
    private TestEntityManager em;
    
    @DisplayName("должен загружать ровно 1 комментарий")
    @Test
    void shouldReturnOneComment() {
        Comment comment = repositoryJPA.getById(3);
        assertThat(comment).isNotNull()
                .matches(c -> !c.getText().isEmpty());
    }

    
    @DisplayName("должен удалять комментарий")
    @Test
    void shouldDeleteComment() {
        repositoryJPA.deleteById(3);
        Comment comment = repositoryJPA.getById(3);
        assertThat(comment).isNull();
    }
}