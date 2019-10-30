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
class CommentDaoJPATest {

    @Autowired
    private CommentDao repository;

    @Autowired
    private BookDao repositoryBook;

    @Autowired
    private TestEntityManager em;

    @DisplayName("должен добавлять ровно 1 комментарий к книге")
    @Test
    void shouldAddOneCommentToBook() {
        Book book = repositoryBook.findById(1L).get();
        Comment comment = new Comment(0, "Новый комментатрий", book);
        repository.save(comment);
        Comment commentFromDB = em.find(Comment.class, comment.getId());
        assertThat(commentFromDB).isEqualTo(comment);
    }
    
    @DisplayName("должен загружать ровно 1 комментарий")
    @Test
    void shouldReturnOneComment() {
        Comment comment = repository.findById(3L).get();
        assertThat(comment).isNotNull()
                .matches(c -> !c.getText().isEmpty());
    }

    
    @DisplayName("должен удалять комментарий")
    @Test
    void shouldDeleteComment() {
        repository.deleteById(3L);
        Comment commentFromDB = em.find(Comment.class, 3L);
        assertThat(commentFromDB).isNull();
    }
}