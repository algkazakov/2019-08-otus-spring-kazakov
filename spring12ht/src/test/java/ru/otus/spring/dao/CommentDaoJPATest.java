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
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;


import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("Репозиторий на основе JPA для работы с комментариями к книгам ")
@DataMongoTest
@EnableConfigurationProperties
@ComponentScan({"ru.otus.spring.config", "ru.otus.spring.dao"})
class CommentDaoJPATest {

    @Autowired
    private CommentDao repository;

    @Autowired
    private BookDao repositoryBook;

    @DisplayName("должен добавлять ровно 1 комментарий к книге")
    @Test
    void shouldAddOneCommentToBook() {
        val books = repositoryBook.findAll();
        Book book = books.get(0);
        Comment comment = new Comment("Новый комментатрий", book);
        Comment commentFromDB = repository.save(comment);
        assertThat(commentFromDB).isEqualTo(comment);
    }
    
    @DisplayName("должен загружать ровно 1 комментарий")
    @Test
    void shouldReturnOneComment() {
        val comments = repository.findAll();
        Comment comment = comments.get(0);
        assertThat(comment).isNotNull()
                .matches(c -> !c.getText().isEmpty());
    }

    @DisplayName("должен удалять комментарий")
    @Test
    void shouldDeleteComment() {
        val comments = repository.findAll();
        Comment comment = comments.get(0);
        repository.deleteById(comment.getId());
        val oldComment = repository.findById(comment.getId());
        assertThat(oldComment).isEmpty();
    }
}