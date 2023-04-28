package com.example.genshinwiki.domain.enums;

import com.example.genshinwiki.domain.abstracts.Article;
import com.example.genshinwiki.domain.entity.User;
import com.example.genshinwiki.domain.entity.article.Tip;
import com.example.genshinwiki.domain.entity.article.Trend;
import lombok.AllArgsConstructor;

import java.util.function.Function;

/**
 * @author zxd
 * @date 2022/6/14 9:51
 */
@AllArgsConstructor
public enum ArticleType {
    /**
     * 攻略
     */
    TIP(Tip::new),
    /**
     * 动态
     */
    TREND(Trend::new);

    public final Function<User, Article> function;

    public Article apply(User user) {
        return this.function.apply(user);
    }
}
