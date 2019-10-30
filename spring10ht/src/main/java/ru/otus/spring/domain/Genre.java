package ru.otus.spring.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "GENRES")
public @Data
class Genre {

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
    public boolean equals(Object genre) {
        return this.id == ((Genre) genre).getId();
    }

    @Override
    public int hashCode() {
        return Long.hashCode(this.id);
    }
}
