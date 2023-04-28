package com.example.genshinwiki.application.services;

import com.example.genshinwiki.application.dto.input.ArticleInput;
import com.example.genshinwiki.application.dto.output.ArticleSimple;
import com.example.genshinwiki.domain.abstracts.Article;
import com.example.genshinwiki.infrastructure.model.PageInput;
import com.example.genshinwiki.infrastructure.model.PageOutPut;

import java.util.List;

/**
 * @author zxd
 * @date 2022/6/7 9:02
 */
public interface ArticleService {
    /**
     * 新建攻略
     *
     * @param token 作者token
     * @param input 文章基本属性
     * @return 文章概要
     */
    ArticleSimple insertTip(String token, ArticleInput input);

    /**
     * 新建动态
     *
     * @param token 作者token
     * @param input 文章基本属性
     * @return 文章概要
     */
    ArticleSimple insertTrend(String token, ArticleInput input);

    /**
     * 更新文章 (编辑)
     *
     * @param token token
     * @param id    文章id
     * @param input 文章基本属性
     * @return 文章概要
     */
    ArticleSimple update(String token, String id, ArticleInput input);

    /**
     * 删除文章
     *
     * @param token token
     * @param id    文章id
     */
    void delete(String token, String id);

    /**
     * 通过id查找文章
     *
     * @param id 文章id
     * @return 文章实体
     */
    Article findBy(String id);

    /**
     * 分页获取文章 (随机)
     *
     * @param pageInput 分页input
     * @return 分页查询结果
     */
    PageOutPut<Article> pageBy(PageInput pageInput);

    /**
     * 发布(送审)
     *
     * @param token t
     * @param id    文章id
     * @return 文章实体
     */
    ArticleSimple release(String token, String id);

    /**
     * 获取待审核的文章列表
     *
     * @return 文章概要列表
     */
    List<ArticleSimple> listByExamine(String token);

    /**
     * 审核文章
     *
     * @param id     文章id
     * @param status 审核状态(结果)
     * @return 文章实体
     */
    Article examine(String token,String id, String status);

    /**
     * 评论
     *
     * @param id      文章id
     * @param token   用户token
     * @param context 评论内容
     * @return 文章实体
     */
    Article comment(String token, String id, String context);

    /**
     * 点赞
     *
     * @param id    文章id
     * @param token 用户token
     * @return 文章实体
     */
    ArticleSimple support(String token, String id);

    /**
     * 取消点赞
     *
     * @param token 用户token
     * @param id    文章id
     * @return 文章实体
     */
    ArticleSimple unSupport(String token, String id);

    /**
     * 置顶文章
     *
     * @param token 用户token
     * @param id    文章id
     * @return 用户实体
     */
    Article setTop(String token, String id);

    /**
     * 获取置顶文章
     *
     * @return 文章List
     */
    List<ArticleSimple> getTops();

    /**
     * 收藏文章
     *
     * @param token 用户token
     * @param id    文章id
     * @return 文章实体
     */
    ArticleSimple collection(String token, String id);

    /**
     * 取消收藏文章
     *
     * @param token 用户token
     * @param id    文章id
     * @return 文章实体
     */
    ArticleSimple unCollection(String token, String id);

    /**
     * 获取文章列表
     *
     * @return 文章列表
     */
    List<ArticleSimple> list();


    List<ArticleSimple> listBy(String type);

    List<ArticleSimple> listBySubject(String subjectId);

}