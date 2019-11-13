package ru.otus.spring.service;

public interface BookService {
    void loadAndPrintBookList();
    void loadAndPrintBookListLite();
    void add();
    void edit();
    void remove();
}
