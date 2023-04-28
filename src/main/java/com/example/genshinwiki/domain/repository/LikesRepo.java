package com.example.genshinwiki.domain.repository;

import com.example.genshinwiki.domain.entity.Likes;
import com.example.genshinwiki.domain.entity.User;
import com.example.genshinwiki.infrastructure.common.entity.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.*;

/**
 * @author zxd
 * @date 2022/6/3 20:47
 */
@Repository
@SuppressWarnings({"AlibabaAbstractMethodOrInterfaceMethodMustUseJavadoc"})
public interface LikesRepo extends BaseRepository<Likes> {
    @Override
    default Likes findByIdOrThrow(String id) {
        return this.findById(id).orElseThrow(() -> new RuntimeException("未找到指定id对应的实体"));
    }
}
