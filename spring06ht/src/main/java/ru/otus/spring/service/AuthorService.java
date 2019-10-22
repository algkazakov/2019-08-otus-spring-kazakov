package ru.otus.spring.service;

import java.util.List;

public interface AuthorService {
    void loadAndPrintAuthorList();
    void add();
    void edit();
    void remove();
}
