package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "genres")
public @Data
class Genre {

    @Id
    private String id;
    @Field("name")
    private String name;

    public Genre(String name) { this.name = name;}

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object genre) {
        return this.id.equals(((Genre) genre).getId());
    }
}
