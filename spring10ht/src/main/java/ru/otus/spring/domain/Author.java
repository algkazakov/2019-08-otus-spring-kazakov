package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "AUTHORS")
public @Data
class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "NAME", nullable = false, unique = true)
    private String name;

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object author) {
        return this.id == ((Author) author).getId();
    }

    @Override
    public int hashCode() {
        return Long.hashCode(this.id);
    }
}
