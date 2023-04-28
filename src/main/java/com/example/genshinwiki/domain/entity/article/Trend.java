package com.example.genshinwiki.domain.entity.article;

import com.example.genshinwiki.domain.abstracts.Article;
import com.example.genshinwiki.domain.entity.User;
import com.example.genshinwiki.domain.enums.ArticleType;
import lombok.Getter;

import javax.persistence.Entity;

/**
 * 动态
 *
 * @author zxd
 * @date 2022/6/3 20:37
 */
@Entity
@Getter
public class Trend extends Article {
    protected Trend() {
    }

    public Trend(User user) {
        super(user, ArticleType.TREND);
    }
}
