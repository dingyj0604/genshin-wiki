package com.example.genshinwiki.domain.abstracts;

import com.example.genshinwiki.domain.entity.Comment;
import com.example.genshinwiki.domain.entity.Likes;
import com.example.genshinwiki.domain.entity.Subject;
import com.example.genshinwiki.domain.entity.User;
import com.example.genshinwiki.domain.enums.ArticleStatus;
import com.example.genshinwiki.domain.enums.ArticleType;
import com.example.genshinwiki.infrastructure.common.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 文章实体
 *
 * @author zxd
 * @date 2022/6/3 19:11
 */
@Entity
@Getter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Article extends BaseEntity {
    /**
     * 文章类型
     */
    @Enumerated(EnumType.STRING)
    private ArticleType type;
    /**
     * 标题
     */
    @Setter
    private String title;
    /**
     * 内容
     */
    @Lob
    @Setter
    @Column(columnDefinition = "TEXT")
    private String content;
    /**
     * 阅读量
     */
    @Setter
    private int readingVolume;
    /**
     * 置顶
     */
    @Setter
    private boolean topping;
    /**
     * 作者
     */
    @ManyToOne
    private User author;
    /**
     * 话题
     */
    @Setter
    @ManyToMany
    private List<Subject> subjects = new ArrayList<>();
    /**
     * 点赞
     */
    @Setter
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Likes> likes = new ArrayList<>();
    /**
     * 评论
     */
    @Setter
    @OneToMany
    private List<Comment> comments = new ArrayList<>();
    /**
     * 发布状态
     */
    @Setter
    @Enumerated(value = EnumType.STRING)
    private ArticleStatus status;

    public int getContentSize() {
        return content.length();
    }

    protected Article() {

    }

    public Article(User user, ArticleType type) {
        this.author = user;
        this.type = type;
        this.status = ArticleStatus.Editing;
    }
}
