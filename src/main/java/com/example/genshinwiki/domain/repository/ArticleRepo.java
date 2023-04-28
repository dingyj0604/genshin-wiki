package com.example.genshinwiki.domain.repository;

import com.example.genshinwiki.domain.abstracts.Article;
import com.example.genshinwiki.domain.entity.User;
import com.example.genshinwiki.domain.enums.ArticleStatus;
import com.example.genshinwiki.domain.enums.ArticleType;
import com.example.genshinwiki.infrastructure.common.entity.BaseRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * @author zxd
 * @date 2022/6/3 20:48
 */
@SuppressWarnings({"AlibabaAbstractMethodOrInterfaceMethodMustUseJavadoc"})
public interface ArticleRepo extends BaseRepository<Article>, JpaSpecificationExecutor<Article> {
    List<Article> findAllByAuthorIs(User author);

    List<Article> findAllByStatusIs(ArticleStatus status);

    @Override
    Page<Article> findAll(Pageable pageable);

    @Override
    default Article findByIdOrThrow(String id) {
        return this.findById(id).orElseThrow(() -> new RuntimeException("未找到指定id的文章"));
    }

    List<Article> findAllByTypeIsAndStatusIs(ArticleType type, ArticleStatus status);

    List<Article> findAllByToppingIsAndStatusIs(boolean topping, ArticleStatus status);

    @Query(value =
            "select * from article " +
                    "where article.status = ?1 and ?2 in (" +
                    "select subjects_id from article_subjects where article.id = article_id)"
            , nativeQuery = true)
    List<Article> findAllBySubjectAndStatusIs(String status,String subjectId);
}
