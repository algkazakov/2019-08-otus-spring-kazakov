package ru.otus.spring.dao;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.context.annotation.Import;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;


import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе JPA для работы с комментариями к книгам ")
@DataJpaTest
@Import({CommentDaoJPA.class, BookDaoJPA.class})
class CommentDaoJPATest {

    @Autowired
    private CommentDaoJPA repositoryJPA;

    @Autowired
    private BookDaoJPA repositoryBookJPA;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен добавлять ровно 1 комментарий к книге")
    @Test
    void shouldAddOneCommentToBook() {
        Book book = repositoryBookJPA.getById(1);
        Comment comment = new Comment(0, "Новый комментатрий", book);
        repositoryJPA.insert(comment);
        Comment commentFromDB = em.find(Comment.class, comment.getId());
        assertThat(commentFromDB).isEqualTo(comment);
    }
    
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
        Comment commentFromDB = em.find(Comment.class, 3L);
        assertThat(commentFromDB).isNull();
    }
}