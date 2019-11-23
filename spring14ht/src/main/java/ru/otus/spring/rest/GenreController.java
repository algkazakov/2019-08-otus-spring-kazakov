package ru.otus.spring.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Genre;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
public class GenreController {

    private final GenreDao repository;

    @Autowired
    public GenreController(GenreDao repository) {
        this.repository = repository;
    }

    @GetMapping("/genre")
    public String listGenre(Model model,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size) {
        List<Genre> genres = repository.findAll();
        int currentPage = page.orElse(1);
        int pageSize = size.orElse(5);

        Page<Genre> genrePage = findPaginated(PageRequest.of(currentPage - 1, pageSize), genres);

        model.addAttribute("genrePage", genrePage);

        int totalPages = genrePage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("pageNumber", currentPage);
        }
        return "listGenre";
    }

    @GetMapping("/genre/new")
    public String newGenre(Genre genre) {
        return "addGenre";
    }

    @PostMapping("/genre/add")
    public String addGenre(Genre genre,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size,
                           Model model) {
        repository.save(genre);
        return listGenre(model, page, size);
    }

    @GetMapping("/genre/edit/{id}")
    public String editGenre(@PathVariable("id") String id,
                            @RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size,
                            Model model) {
        Genre genre = repository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("genre", genre);
        setPageNum(page, size, model);
        return "editGenre";
    }

    @PostMapping("/genre/update/{id}")
    public String updateGenre(
            @PathVariable("id") String id,Genre genre,
            @RequestParam("page") Optional<Integer> page,
            @RequestParam("size") Optional<Integer> size,
            Model model
    ) {
        repository.save(genre);
        return listGenre(model, page, size);
    }

    @GetMapping("/genre/delete/{id}")
    public String deleteGenre(@PathVariable("id") String id,
                              @RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size,
                              Model model) {
        Genre genre = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid genre Id:" + id));
        repository.delete(genre);
        return listGenre(model, page, size);
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

    private Page<Genre> findPaginated(Pageable pageable, List<Genre> genres) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Genre> list;

        if (genres.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, genres.size());
            list = genres.subList(startItem, toIndex);
        }

        Page<Genre> genrePage
                = new PageImpl<Genre>(list, PageRequest.of(currentPage, pageSize), genres.size());

        return genrePage;
    }
}