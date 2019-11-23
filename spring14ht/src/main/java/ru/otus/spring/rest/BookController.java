package ru.otus.spring.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.dao.AuthorDao;
import ru.otus.spring.dao.BookDao;
import ru.otus.spring.dao.CommentDao;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Author;
import ru.otus.spring.domain.Book;
import ru.otus.spring.domain.Comment;
import ru.otus.spring.domain.Genre;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    public String listBook(Model model,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size) {
        List<Book> books = repository.findAll();
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<Book> bookPage = findPaginated(PageRequest.of(currentPage - 1, pageSize), books);

        model.addAttribute("bookPage", bookPage);

        int totalPages = bookPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("pageNumber", currentPage);
        }
        return "list";
    }

    @GetMapping("/new")
    public String newBook(Book book) {
        return "add";
    }

    @PostMapping("/add")
    public String addBook(@Valid Book book,
                          BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add";
        }
        repository.save(book);
        return listBook(model, Optional.empty(), Optional.empty());
    }

    @GetMapping("/edit/{id}")
    public String editBook(@PathVariable("id") String id,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size, Model model) {
        Book book = repository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("book", book);
        model.addAttribute("comments", repositoryComment.findByBook(book));
        setPageNum(page, size, model);
        return "edit";
    }

    private void setPageNum(@RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size, Model model) {
        Integer pageNum = page.orElse(1);
        if (pageNum > 1) {
            model.addAttribute("page", pageNum);
        }
        Integer sizeNum = size.orElse(1);
        if (sizeNum > 1) {
            model.addAttribute("size", sizeNum);
        }
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
        return listBook(model, page, size);
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable("id") String id,
                             @RequestParam("page") Optional<Integer> page,
                             @RequestParam("size") Optional<Integer> size,
                             Model model) {
        Book book = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        repository.delete(book);
        return listBook(model, page, size);
    }

    @GetMapping("/addAuthor")
    public String editBookAuthor(@RequestParam("bookId") String id, Model model) {
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
        setPageNum(page, size, model);
        return editBook(id, page, size, model);
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
        setPageNum(page, size, model);
        return editBook(id, page, size, model);
    }

    @GetMapping("/addGenre")
    public String editBookGenre(@RequestParam("bookId") String id, Model model) {
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
        Book book = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        book.getGenres().add(genre);
        repository.save(book);
        setPageNum(page, size, model);
        return editBook(id, page, size, model);
    }

    @GetMapping("/deleteGenre")
    public String deleteGenre(@RequestParam("bookId") String id,
                               @RequestParam("id") String genreId,
                              @RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size,
                              Model model) {
        Book book = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        Genre genre = repositoryGenre.findById(genreId).orElseThrow(NotFoundException::new);
        book.getGenres().remove(genre);
        repository.save(book);
        setPageNum(page, size, model);
        return editBook(id, page, size, model);
    }

    @GetMapping("/addComment")
    public String addComment(@RequestParam("bookId") String id, Comment comment, Model model) {
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
        setPageNum(page, size, model);
        return editBook(id, page, size, model);
    }

    @GetMapping("/deleteComment")
    public String deleteComment(@RequestParam("bookId") String id,
                               @RequestParam("id") String commentId,
                                @RequestParam("page") Optional<Integer> page,
                                @RequestParam("size") Optional<Integer> size,
                               Model model) {
        repositoryComment.delete(repositoryComment.findById(commentId).orElseThrow(NotFoundException::new));
        setPageNum(page, size, model);
        return editBook(id, page, size, model);
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

    private Page<Book> findPaginated(Pageable pageable, List<Book> books) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Book> list;

        if (books.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, books.size());
            list = books.subList(startItem, toIndex);
        }

        Page<Book> bookPage
                = new PageImpl<Book>(list, PageRequest.of(currentPage, pageSize), books.size());

        return bookPage;
    }
}
