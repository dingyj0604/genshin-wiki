package com.example.genshinwiki.application.dto.input;

import lombok.Data;

import javax.persistence.Column;


/**
 * @author zxd
 * @date 2022/6/6 9:23
 */
@Data
public class SubjectInput {
    /**
     * 标题
     */
    @Column(unique = true)
    private String title;
    /**
     * 简介
     */
    private String introduce;
}
