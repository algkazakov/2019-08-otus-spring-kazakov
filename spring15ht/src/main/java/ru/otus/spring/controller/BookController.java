package ru.otus.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.dao.CommentDao;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.util.PageUtil;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Controller
public class BookController {

    private final BookDao repository;
    private final AuthorDao repositoryAuthor;
    private final GenreDao repositoryGenre;
    private final CommentDao repositoryComment;

    @Autowired
    public BookController(BookDao repository, AuthorDao repositoryAuthor,
            GenreDao repositoryGenre,CommentDao repositoryComment) {
        this.repository = repository;
        this.repositoryAuthor = repositoryAuthor;
        this.repositoryGenre = repositoryGenre;
        this.repositoryComment = repositoryComment;
    }

    @GetMapping("/")
    public String getBookListView(Model model,
                                  @RequestParam("page") Optional<Integer> page,
                                  @RequestParam("size") Optional<Integer> size) {
        List<Book> books = repository.findAll();
        PageUtil.setPageInfoModel(books, page, size, model);
        return "list";
    }


    @GetMapping("/new")
    public String addNewBookView(Book book) {
        return "add";
    }

    @PostMapping("/add")
    public String addBook(@Valid Book book, Model model) {
        repository.save(book);
        return "redirect:/";
    }

    @GetMapping("/edit/{id}")
    public String editBookView(@PathVariable("id") String id,
                               @RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> size, Model model) {
        Book book = repository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("book", book);
        model.addAttribute("comments", repositoryComment.findByBook(book));
        PageUtil.setPageNum(page, size, model);
        return "edit";
    }

    @PostMapping("/update/{id}")
    public String updateBook(
            @PathVariable("id") String id,
            @RequestParam("name") String name,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            Model model
    ) {
        Book book = repository.findById(id).orElseThrow(NotFoundException::new);
        book.setName(name);
        repository.save(book);
        return "redirect:/?page=" + page.orElse(1) + "&size=" + size.orElse(5);
    }

    @PostMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") String id,
                             @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size") Optional<Integer> size,
                             Model model) {
        repository.deleteById(id);
        return "redirect:/?page=" + page.orElse(1) + "&size=" + size.orElse(5);
    }

    @GetMapping("/addAuthor")
    public String editBookAuthorView(@RequestParam("bookId") String id, Model model) {
        model.addAttribute("authors", repositoryAuthor.findAll());
        model.addAttribute("author", new Author());
        model.addAttribute("bookId", id);
        return "addBookAuthor";
    }

    @PostMapping("/updateAuthor")
    public String updateAuthor(
            @RequestParam("bookId") String id, Author author,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size, Model model
    ) {
        Book book = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        book.getAuthors().add(author);
        repository.save(book);
        PageUtil.setPageNum(page, size, model);
        return "redirect:/edit/" + id + "?page=" + page.orElse(1) + "&size=" + size.orElse(5);
    }

    @GetMapping("/deleteAuthor")
    public String deleteAuthor(@RequestParam("bookId") String id,
                               @RequestParam("id") String authorId,
                               @RequestParam("page") Optional<Integer> page,
                               @RequestParam("size") Optional<Integer> size,
                               Model model) {
        Book book = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        Author author = repositoryAuthor.findById(authorId).orElseThrow(NotFoundException::new);
        book.getAuthors().remove(author);
        repository.save(book);
        PageUtil.setPageNum(page, size, model);
        return "redirect:/edit/" + id + "?page=" + page.orElse(1) + "&size=" + size.orElse(5);
    }

    @GetMapping("/addGenre")
    public String editBookGenreView(@RequestParam("bookId") String id, Model model) {
        model.addAttribute("genres", repositoryGenre.findAll());
        model.addAttribute("genre", new Genre());
        model.addAttribute("bookId", id);
        return "addBookGenre";
    }

    @PostMapping("/updateGenre")
    public String updateGenre(
            @RequestParam("bookId") String id, Genre genre,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            Model model
    ) {
        Book book = repository.findById(id).orElseThrow(NotFoundException::new);
        book.getGenres().add(genre);
        repository.save(book);
        PageUtil.setPageNum(page, size, model);
        return "redirect:/edit/" + id + "?page=" + page.orElse(1) + "&size=" + size.orElse(5);
    }

    @GetMapping("/deleteGenre")
    public String deleteGenre(@RequestParam("bookId") String id,
                               @RequestParam("id") String genreId,
                              @RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size,
                              Model model) {
        Book book = repository.findById(id).orElseThrow(NotFoundException::new);
        Genre genre = repositoryGenre.findById(genreId).orElseThrow(NotFoundException::new);
        book.getGenres().remove(genre);
        repository.save(book);
        PageUtil.setPageNum(page, size, model);
        return "redirect:/edit/" + id + "?page=" + page.orElse(1) + "&size=" + size.orElse(5);
    }

    @GetMapping("/addComment")
    public String addCommentView(@RequestParam("bookId") String id, Comment comment, Model model) {
        model.addAttribute("bookId", id);
        return "addComment";
    }

    @PostMapping("/addComment")
    public String updateComment(@RequestParam("bookId") String id, Comment comment,
                                @RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size,
                                Model model) {
        Book book = repository.findById(id).orElseThrow(NotFoundException::new);
        comment.setBook(book);
        repositoryComment.save(comment);
        PageUtil.setPageNum(page, size, model);
        return "redirect:/edit/" + id + "?page=" + page.orElse(1) + "&size=" + size.orElse(5);
    }

    @GetMapping("/deleteComment")
    public String deleteComment(@RequestParam("bookId") String id,
                               @RequestParam("id") String commentId,
                                @RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size,
                               Model model) {
        repositoryComment.delete(repositoryComment.findById(commentId).orElseThrow(NotFoundException::new));
        PageUtil.setPageNum(page, size, model);
        return "redirect:/edit/" + id + "?page=" + page.orElse(1) + "&size=" + size.orElse(5);
    }

    @ExceptionHandler(NotFoundException.class)
    public String handleNFE(NotFoundException e, Model model, HttpServletRequest request) {
        model.addAttribute("timestamp", new Date());
        model.addAttribute("message", e.getMessage());
        model.addAttribute("error", "Internal Server Error");
        model.addAttribute("status", "500");
        model.addAttribute("path", request.getRequestURI());
        model.addAttribute("exception", e.getClass().getName());
        return "error";
    }
}
