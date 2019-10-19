package ru.otus.spring.domain;

import lombok.Data;

public @Data
class Author {

    private final long id;
    private final String name;

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
