package com.example.genshinwiki.infrastructure.model;

import lombok.Data;

/**
 * 分页入参
 *
 * @date 2021-03-29 11:53
 */
@Data
public class PageInput {
    /**
     * 页码 0表示第一页
     */
    private Integer page;
    /**
     * 页长 每页记录条数
     */
    private Integer size;
}
