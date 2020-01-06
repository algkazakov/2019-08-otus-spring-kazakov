package ru.otus.spring.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.ui.Model;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PageUtil {

    public static <T> void setPageInfoModel(List<T> objects, Optional<Integer> pageNumber, Optional<Integer> size, Model model) {
        int currentPage = pageNumber.orElse(1);
        int pageSize = size.orElse(5);
        Page<T> page = findPaginated(PageRequest.of(currentPage - 1, pageSize), objects);
        model.addAttribute("page", page);
        List<Integer> pageNumbers = null;
        int totalPages = page.getTotalPages();
        if (totalPages > 0) {
            pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed().collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
            model.addAttribute("pageNumber", currentPage);
        }
    }

    public static <T> Page<T> findPaginated(Pageable pageable, List<T> objects) {
        int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<T> list;

        if (objects.size() < startItem) {
            list = Collections.emptyList();
        } else {
            int toIndex = Math.min(startItem + pageSize, objects.size());
            list = objects.subList(startItem, toIndex);
        }

        Page<T> page = new PageImpl<T>(list, PageRequest.of(currentPage, pageSize), objects.size());

        return page;
    }

    public static void setPageNum(Optional<Integer> page, Optional<Integer> size, Model model) {
        Integer pageNum = page.orElse(1);
        if (pageNum > 1) {
            model.addAttribute("page", pageNum);
        }
        Integer sizeNum = size.orElse(1);
        if (sizeNum > 1) {
            model.addAttribute("size", sizeNum);
        }
    }
}
