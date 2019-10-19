package ru.otus.spring.domain;

import lombok.Data;

public @Data
class Genre {

    private final long id;
    private final String name;

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
