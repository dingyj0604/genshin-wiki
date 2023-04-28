package com.example.genshinwiki.application.dto.input;

import lombok.Data;

import java.util.List;

/**
 * @author zxd
 * @date 2022/6/7 9:03
 */
@Data
public class ArticleInput {
    /**
     * 文章类型
     */
    private String type;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 话题 id
     */
    private List<String> subjectIds;
}
