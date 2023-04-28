package com.example.genshinwiki.application.dto.output;

import cn.hutool.core.bean.BeanUtil;
import com.example.genshinwiki.domain.abstracts.Article;
import com.example.genshinwiki.domain.entity.Subject;
import com.example.genshinwiki.domain.entity.User;
import com.example.genshinwiki.domain.enums.ArticleStatus;
import com.example.genshinwiki.domain.repository.LikesRepo;
import com.example.genshinwiki.infrastructure.util.IocUtil;
import lombok.Data;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zxd
 * @date 2022/6/12 20:38
 */
@Data
public class ArticleSimple {
    /**
     * id
     */
    private String id;
    /**
     * 标题
     */
    private String title;
    /**
     * 内容
     */
    private String content;
    /**
     * 阅读量
     */
    private int readingVolume;
    /**
     * 置顶
     */
    private boolean topping;
    /**
     * 作者
     */
    private User author;
    /**
     * 话题
     */
    private List<Subject> subjects;
    /**
     * 点赞数
     */
    private int likeNum;
    /**
     * 评论数
     */
    private int commentNum;
    /**
     * 发布状态
     */
    private ArticleStatus status;
    /**
     * 文章长度
     */
    private int contentSize;

    public ArticleSimple(Article article) {
        int simpleSize = 50;
        BeanUtil.copyProperties(article, this);
        this.likeNum = article.getLikes().size();
        this.commentNum = article.getComments().size();
        if (article.getContent().length() > simpleSize) {
            this.content = article.getContent().substring(0, simpleSize);
        }
        this.contentSize = article.getContentSize();}

    public static List<ArticleSimple> toSimples(List<Article> articles) {
        return articles.stream().map(ArticleSimple::new).collect(Collectors.toList());
    }

}
