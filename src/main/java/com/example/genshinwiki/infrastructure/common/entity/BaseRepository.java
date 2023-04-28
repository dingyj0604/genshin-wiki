package com.example.genshinwiki.infrastructure.common.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @author zxd
 * @date 2022/6/2 20:54
 */
@NoRepositoryBean
public interface BaseRepository<T> extends JpaRepository<T, String> {
    /**
     * 通过id查找实体，找不到则抛出异常
     *
     * @param id 实体id
     * @return 实体
     */
    T findByIdOrThrow(String id);
}
