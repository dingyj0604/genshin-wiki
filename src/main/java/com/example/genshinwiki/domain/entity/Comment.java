package com.example.genshinwiki.domain.entity;

import com.example.genshinwiki.application.dto.output.UserSimple;
import com.example.genshinwiki.infrastructure.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * 评论 实体
 *
 * @author zxd
 * @date 2022/6/3 19:23
 */
@Entity
@Getter
public class Comment extends BaseEntity {
    /**
     * 评论内容
     */
    @Setter
    private String context;
    /**
     * 评论者
     */
    @ManyToOne
    private User commentator;
    /**
     * 置顶
     */
    @Setter
    private boolean topping;
    /**
     * 回复对象(回复其他评论
     */
    @Setter
    @ManyToOne
    private Comment replyTo;
    /**
     * 点赞
     */
    @Setter
    @OneToMany
    private List<Likes> likes;

    public UserSimple getCommentator() {
        return UserSimple.of(commentator);
    }

    protected Comment() {
    }

    public Comment(User user) {
        this.commentator = user;
    }
}
