package ru.otus.spring.domain;

public class Genre {

    private final long id;
    private final String name;

    public Genre(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

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
