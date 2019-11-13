package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "authors")
public @Data
class Author {

    @Id
    private String id;
    @Field("name")
    private String name;

    public Author(String name) { this.name = name;}

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object author) {
        return this.id.equals(((Author) author).getId());
    }
}
