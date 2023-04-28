package com.example.genshinwiki.domain.entity;

import com.example.genshinwiki.infrastructure.common.entity.BaseEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 * 点赞 实体
 *
 * @author zxd
 * @date 2022/6/3 19:23
 */
@Entity
@Getter
public class Likes extends BaseEntity {
    /**
     * 点赞者
     */
    @ManyToOne
    @JsonIgnore
    private User user;


    protected Likes() {
    }

    public String getUserId(){
        return user.getId();
    }
    public Likes(User user) {
        this.user = user;
    }
}
