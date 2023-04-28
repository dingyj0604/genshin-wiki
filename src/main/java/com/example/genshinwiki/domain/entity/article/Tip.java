package com.example.genshinwiki.domain.entity.article;

import com.example.genshinwiki.domain.abstracts.Article;
import com.example.genshinwiki.domain.entity.User;
import com.example.genshinwiki.domain.enums.ArticleType;

import javax.persistence.Entity;

/**
 * 攻略
 *
 * @author zxd
 * @date 2022/6/3 20:35
 */
@Entity
public class Tip extends Article {
    protected Tip() {
    }

    public Tip(User user) {
        super(user, ArticleType.TIP);
    }
}
