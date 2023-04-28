package com.example.genshinwiki.domain.enums;

/**
 * 发布状态 枚举
 *
 * @author zxd
 * @date 2022/6/3 20:16
 */
public enum ArticleStatus {
    /**
     * 编辑中
     */
    Editing,
    /**
     * 审核中
     */
    Reviewing,
    /**
     * 已发布
     */
    Published,
    /**
     * 审核未通过
     */
    Failed,
    /**
     * 私密
     */
    Private
}
