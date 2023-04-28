package com.example.genshinwiki.domain.entity;

import com.example.genshinwiki.application.dto.output.UserSimple;
import com.example.genshinwiki.infrastructure.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * 话题
 *
 * @author zxd
 * @date 2022/6/2 21:48
 */
@Entity
@Getter
public class Subject extends BaseEntity {
    /**
     * 标题
     */
    @Setter
    private String title;
    /**
     * 简介
     */
    @Setter
    private String introduce;
    /**
     * 创建者
     */
    @Setter
    @ManyToOne
    @JsonIgnore
    private User author;
    @Setter
    private String updateBy;
    /**
     * 讨论度
     */
    @Setter
    private int discussion;

    public UserSimple getAuthor() {
        return UserSimple.of(author);
    }

    public Subject() {

    }

}
