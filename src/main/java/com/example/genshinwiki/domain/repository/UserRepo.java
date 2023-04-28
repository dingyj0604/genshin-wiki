package com.example.genshinwiki.domain.repository;

import com.example.genshinwiki.domain.entity.User;
import com.example.genshinwiki.infrastructure.common.entity.BaseRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author zxd
 * @date 2022/6/3 20:46
 */
@Repository
@SuppressWarnings({"AlibabaAbstractMethodOrInterfaceMethodMustUseJavadoc"})
public interface UserRepo extends BaseRepository<User> {
    User findUserByUsernameIs(String username);

    default User findByIdOrThrow(String id) {
        return this.findById(id).orElseThrow(() -> new RuntimeException("未找到指定id的用户"));
    }

    @Query(value =
            "select * from user " +
                    "where ?1 in (" +
                    "select follows_id from user_follows where user.id = user_id)"
            , nativeQuery = true)
    List<User> getAllByFollow(String userId);

}
