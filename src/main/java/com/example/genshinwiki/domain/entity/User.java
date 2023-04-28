package com.example.genshinwiki.domain.entity;

import cn.hutool.core.io.FileUtil;
import com.example.genshinwiki.domain.abstracts.Article;
import com.example.genshinwiki.domain.enums.Role;
import com.example.genshinwiki.domain.repository.ArticleRepo;
import com.example.genshinwiki.domain.repository.CommentRepo;
import com.example.genshinwiki.domain.repository.UserRepo;
import com.example.genshinwiki.infrastructure.common.entity.BaseEntity;
import com.example.genshinwiki.infrastructure.consts.AppConst;
import com.example.genshinwiki.infrastructure.util.IocUtil;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 用户 实体
 *
 * @author zxd
 * @date 2022/6/2 16:06
 */
@Entity
@Getter
@ToString
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"username"})})
public class User extends BaseEntity {
    /**
     * 用户名
     */
    @Setter
    @Column(unique = true)
    private String username;
    /**
     * 昵称
     */
    @Setter
    private String name;
    /**
     * 签名
     */
    @Setter
    private String autograph;
    /**
     * 头像
     */
    @Setter
    private String portrait;
    /**
     * 经验
     */
    @Setter
    private String experience;
    /**
     * 密码
     */
    @Setter
    @JsonIgnore
    private String password;
    /**
     * 用户类型
     */
    @Enumerated(value = EnumType.STRING)
        private Role role;
    /**
     * 关注的人
     */
    @Setter
    @OneToMany
    @JsonIgnore
    private List<User> follows = new ArrayList<>();
    /**
     * 关心的话题
     */
    @Setter
    @OneToMany
    @JsonIgnore
    private List<Subject> concerns = new ArrayList<>();
    /**
     * 收藏的文章
     */
    @Setter
    @OneToMany
    @JsonIgnore
    private List<Article> collections = new ArrayList<>();

    public User(Role role) {
        this.role = role;
    }


    public String getPortraitPath() {
        if(this.portrait==null){
            return "";
        }
        return Paths.get(AppConst.Dir.IMG_ROOT_PATH, FileUtil.getName(this.portrait)).toString();
    }

    protected User() {
    }

    public int getFenNum() {
        return IocUtil.get(UserRepo.class).getAllByFollow(getId()).size();
    }

    public int getLikeNum() {
        AtomicInteger num = new AtomicInteger(0);
        List<Article> articles = IocUtil.get(ArticleRepo.class).findAllByAuthorIs(this);
        if (articles != null && articles.size() > 0) {
            articles.forEach(article -> num.addAndGet((article.getLikes().size())));
        }
        List<Comment> comments = IocUtil.get(CommentRepo.class).findAllByCommentatorIs(this);
        if (comments != null && comments.size() > 0) {
            comments.forEach(comment -> num.addAndGet(comment.getLikes().size()));
        }
        return num.get();
    }

    public int getFollow() {
        return follows.size();
    }


}
