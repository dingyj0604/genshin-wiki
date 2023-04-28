package com.example.genshinwiki.domain.repository;

import com.example.genshinwiki.domain.entity.Comment;
import com.example.genshinwiki.domain.entity.User;
import com.example.genshinwiki.infrastructure.common.entity.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zxd
 * @date 2022/6/3 20:50
 */
@Repository
@SuppressWarnings({"AlibabaAbstractMethodOrInterfaceMethodMustUseJavadoc"})
public interface CommentRepo extends BaseRepository<Comment> {
    default Comment findByIdOrThrow(String id) {
        return this.findById(id).orElseThrow(() -> new RuntimeException("未找到指定id的评论"));
    }

    List<Comment> findAllByCommentatorIs(User commentator);
}
