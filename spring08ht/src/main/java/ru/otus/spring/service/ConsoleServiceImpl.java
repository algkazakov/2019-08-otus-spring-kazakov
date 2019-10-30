package ru.otus.spring.service;

import org.springframework.stereotype.Service;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.domain.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

@Service
public class ConsoleServiceImpl implements ConsoleService {

    private final Scanner in = new Scanner(System.in);

    @Override
    public void printBook(Book book) {
        System.out.print(book.getId() + ". ");
        if (!book.getAuthors().isEmpty()) {
            System.out.print(book.getAuthors());
        }
        System.out.print(" " + book.getName());
        if (!book.getGenres().isEmpty()) {
            System.out.print(" (" + book.getGenres() + ")");
        }
        System.out.print("\n");
    }

    @Override
    public void printBookLite(Book book) {
        System.out.print(book.getId() + ". ");
        System.out.print(" " + book.getName());
        System.out.print("\n");
    }

    @Override
    public void printBookList(List<Book> list) {
        for (Book book: list) {
            printBook(book);
        }
    }

    @Override
    public void printBookListLite(List<Book> list) {
        for (Book book: list) {
            printBookLite(book);
        }
    }

    @Override
    public void printAuthorList(List<Author> list) {
        for (Author author: list) {
            System.out.println(author.getId() + ". " + author.getName());
        }
    }

    @Override
    public void printGenreList(List<Genre> list) {
        for (Genre genre: list) {
            System.out.println(genre.getId() + ". " + genre.getName());
        }
    }

    @Override
    public void printCommentList(List<Comment> list) {
        for (Comment comment: list) {
            System.out.println(comment.getId() + ". " + comment.getText());
        }
    }

    @Override
    public String enterBookName() {
        System.out.println("Enter book name:");
        String result = in.nextLine();
        if (result.trim().isEmpty()) {
            System.out.println("Empty book name isn't allowed");
            return enterBookName();
        }
        return result;
    }

    @Override
    public long enterBookNumber() {
        System.out.println("Enter book number:");
        String result = in.nextLine();
        if (result.trim().isEmpty()) {
            System.out.println("Empty book number isn't allowed");
            return enterBookNumber();
        } else {
            return Long.parseLong(result);
        }
    }

    @Override
    public String enterAuthorName() {
        System.out.println("Enter author name:");
        String result = in.nextLine();
        if (result.trim().isEmpty()) {
            System.out.println("Empty author name isn't allowed");
            return enterAuthorName();
        }
        return result;
    }

    @Override
    public long enterAuthorNumber() {
        System.out.println("Enter author number:");
        String result = in.nextLine();
        if (result.trim().isEmpty()) {
            System.out.println("Empty author number isn't allowed");
            return enterAuthorNumber();
        } else {
            return Long.parseLong(result);
        }
    }

    @Override
    public String enterGenreName() {
        System.out.println("Enter genre name:");
        String result = in.nextLine();
        if (result.trim().isEmpty()) {
            System.out.println("Empty genre name isn't allowed");
            return enterGenreName();
        }
        return result;
    }

    @Override
    public long enterGenreNumber() {
        System.out.println("Enter genre number:");
        String result = in.nextLine();
        if (result.trim().isEmpty()) {
            System.out.println("Empty genre number isn't allowed");
            return enterGenreNumber();
        } else {
            return Long.parseLong(result);
        }
    }

    @Override
    public List<Long> enterAuthors(){
        System.out.println("Enter author's numbers (comma separated):");
        String [] authorIds = in.nextLine().split(",");
        try {
            return getLongs(authorIds);
        } catch (NumberFormatException e) {
            System.out.println("You must enter numbers only. Try again.");
            return enterAuthors();
        }
    }

    private List<Long> getLongs(String[] ids) {
        List<Long> result = new ArrayList<>();
        for (String id: ids) {
            result.add(Long.parseLong(id));
        }
        return result;
    }

    @Override
    public List<Long> enterGenres() {
        System.out.println("Enter genre's numbers (comma separated):");
        String [] genreIds = in.nextLine().split(",");
        try {
            return getLongs(genreIds);
        } catch (NumberFormatException e) {
            System.out.println("You must enter numbers only. Try again.");
            return enterGenres();
        }
    }

    @Override
    public String enterComment() {
        System.out.println("Enter comment:");
        String result = in.nextLine();
        if (result.trim().isEmpty()) {
            System.out.println("Empty comment isn't allowed");
            return enterComment();
        }
        return result;
    }

    @Override
    public long enterCommentNumber() {
        System.out.println("Enter comment number:");
        String result = in.nextLine();
        if (result.trim().isEmpty()) {
            System.out.println("Empty comment number isn't allowed");
            return enterCommentNumber();
        } else {
            return Long.parseLong(result);
        }
    }
}