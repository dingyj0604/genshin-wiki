package com.example.genshinwiki.domain.repository;

import com.example.genshinwiki.domain.entity.Subject;
import com.example.genshinwiki.domain.entity.User;
import com.example.genshinwiki.infrastructure.common.entity.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zxd
 * @date 2022/6/3 20:49
 */
@Repository
@SuppressWarnings({"AlibabaAbstractMethodOrInterfaceMethodMustUseJavadoc"})
public interface SubjectRepo extends BaseRepository<Subject> {
    List<Subject> findAllByTitleIsLike(String title);

    List<Subject> findAllByAuthorIs(User user);

    Subject findByTitleIs(String title);

    List<Subject> findAllByOrderByDiscussionDesc();

    default Subject findByIdOrThrow(String id) {
        return this.findById(id).orElseThrow(() -> new RuntimeException("未找到指定id的话题"));
    }
}
