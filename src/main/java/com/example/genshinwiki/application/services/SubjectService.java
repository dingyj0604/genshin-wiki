package com.example.genshinwiki.application.services;

import com.example.genshinwiki.application.dto.input.SubjectInput;
import com.example.genshinwiki.domain.entity.Subject;

import java.util.List;

/**
 * 话题业务接口
 *
 * @author zxd
 * @date 2022/6/6 9:24
 */
public interface SubjectService {
    /**
     * 通过id查找
     *
     * @param id 话题id
     * @return 话题
     */
    Subject getBy(String id);

    /**
     * 通过关键字查找(模糊查询)
     *
     * @param keyword 关键字
     * @return 话题list
     */
    List<Subject> listByKeyword(String keyword);

    /**
     * 通过用户查找
     *
     * @param userId 用户id
     * @return 话题list
     */
    List<Subject> listByUser(String userId);

    /**
     * 创建话题
     *
     * @param token 创建者id
     * @param input 话题input
     * @return 话题实体
     */
    Subject insert(String token, SubjectInput input);

    /**
     * 更新话题
     *
     * @param token 用户token
     * @param id    话题id
     * @param input 更新的话题input
     * @return 话题实体
     */
    Subject update(String token, String id, SubjectInput input);

    /**
     * 删除话题
     *
     * @param token
     * @param id    话题id
     */
    void delete(String token, String id);

    /**
     * 获取所有话题
     *
     * @return 话题列表
     */
    List<Subject> listBy();

    /**
     * 获取热门话题
     *
     * @return 话题列表
     */
    List<Subject> getHots();
}