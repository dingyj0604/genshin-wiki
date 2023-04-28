package com.example.genshinwiki.application.services;

import com.example.genshinwiki.domain.entity.Comment;

/**
 * @author zxd
 * @date 2022/6/6 16:58
 */
public interface CommentService {
    /**
     * 评论回复
     *
     * @param token   用户token
     * @param id      被回复的评论id
     * @param context 回复内容
     * @return 评论实体
     */
    Comment reply(String token, String id, String context);

    /**
     * 点赞
     *
     * @param token 用户token
     * @param id    被点赞的评论
     * @return 评论实体
     */
    Comment support(String token, String id);
}
