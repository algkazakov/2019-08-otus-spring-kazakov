package ru.otus.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.otus.spring.dao.GenreDao;
import ru.otus.spring.domain.Genre;
import ru.otus.spring.util.PageUtil;

import java.util.List;
import java.util.Optional;

@RestController
public class GenreController {

    private final GenreDao repository;

    @Autowired
    public GenreController(GenreDao repository) {
        this.repository = repository;
    }

    @GetMapping("/genre")
    public String getListGenreView(Model model,
                                   @RequestParam("page") Optional<Integer> page,
                                   @RequestParam("size") Optional<Integer> size) {
        List<Genre> genres = repository.findAll();
        PageUtil.setPageInfoModel(genres, page, size, model);
        return "listGenre";
    }

    @GetMapping("/genre/new")
    public String addNewGenreView(Genre genre) {
        return "addGenre";
    }

    @PostMapping("/genre/add")
    public String addGenre(Genre genre,
                           @RequestParam("page") Optional<Integer> page,
                           @RequestParam("size") Optional<Integer> size,
                           Model model) {
        repository.save(genre);
        return "redirect:/genre?page=" + page.orElse(1) + "&size=" + size.orElse(5);
    }

    @GetMapping("/genre/edit/{id}")
    public String editGenre(@PathVariable("id") String id,
                            @RequestParam("page") Optional<Integer> page,
                            @RequestParam("size") Optional<Integer> size,
                            Model model) {
        Genre genre = repository.findById(id).orElseThrow(NotFoundException::new);
        model.addAttribute("genre", genre);
        PageUtil.setPageNum(page, size, model);
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
        return "redirect:/genre?page=" + page.orElse(1) + "&size=" + size.orElse(5);
    }

    @PostMapping("/genre/delete/{id}")
    public String deleteGenre(@PathVariable("id") String id,
                              @RequestParam("page") Optional<Integer> page,
                              @RequestParam("size") Optional<Integer> size,
                              Model model) {
        repository.deleteById(id);
        return "redirect:/genre?page=" + page.orElse(1) + "&size=" + size.orElse(5);
    }
}