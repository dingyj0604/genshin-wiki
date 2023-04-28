package com.example.genshinwiki.infrastructure.model;

import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

/**
 * 分页输出Model
 *
 * @author ankelen
 * @date 2021-02-11 16:05
 */
@Getter
public class PageOutPut<T> {
    private List<T> content;

    private int pageSize;
    private int pageNumber;
    private int totalPages;
    private long totalElements;

    private boolean first;
    private boolean last;

    public static <T> PageOutPut<T> of(Page<T> page) {
        PageOutPut<T> out = new PageOutPut<>();

        out.content = page.getContent();

        out.pageSize = page.getSize();
        out.pageNumber = page.getNumber();
        out.totalPages = page.getTotalPages();
        out.totalElements = page.getTotalElements();

        out.first = page.isFirst();
        out.last = page.isLast();
        return out;
    }
}
