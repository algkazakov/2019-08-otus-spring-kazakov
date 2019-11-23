package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "comments")
public @Data
class Comment {

    @Id
    private String id;
    @Field("text")
    private String text;

    @DBRef
    private Book book;

    public Comment(String text) { this.text = text;}
    public Comment(String text, Book book) {
        this.text = text;
        this.book = book;
    }
}
