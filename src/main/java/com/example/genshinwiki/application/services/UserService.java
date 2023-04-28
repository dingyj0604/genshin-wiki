package com.example.genshinwiki.application.services;

import com.example.genshinwiki.application.dto.input.UserInput;
import com.example.genshinwiki.application.dto.output.ArticleSimple;
import com.example.genshinwiki.application.dto.output.UserOutput;
import com.example.genshinwiki.domain.abstracts.Article;
import com.example.genshinwiki.domain.entity.Subject;
import com.example.genshinwiki.domain.entity.User;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 用户业务接口
 *
 * @author zxd
 * @date 2022/6/5 20:02
 */
public interface UserService {
    /**
     * 关注 用户
     * 如果已关注就取消关注 未关注就关注
     *
     * @param token      用户token
     * @param followedId 被关注的用户的id
     * @return 用户实体
     */
    User follow(String token, String followedId);
    /**
     * 取消关注 用户
     * 如果已关注就取消关注 未关注就关注
     *
     * @param token      用户token
     * @param followedId 被关注的用户的id
     * @return 用户实体
     */
    User unFollow(String token, String followedId);

    /**
     * 关注 话题
     * 如果已关注就取消关注 未关注就关注
     *
     * @param token       用户token
     * @param concernedId 被关注的话题的id
     * @return 用户实体
     */
    User concern(String token, String concernedId);

    /**
     * 收藏 文章
     *
     * @param id           用户id
     * @param collectionId 文章id
     * @return 用户实体
     */
    User collect(String id, String collectionId);

    /**
     * 获取该用户创建的文章
     *
     * @param token 用户token
     * @return 文章列表
     */
    List<ArticleSimple> works(String token);

    /**
     * 通过id 查找用户
     *
     * @param id 用户id
     * @return 用户实体
     */
    User getBy(String id);

    /**
     * 创建用户 (注册）
     *
     * @param username 用户名
     * @param pwd      密码
     * @return 用户实体
     */
    User insert(String username, String pwd);

    /**
     * 更新用户信息
     *
     * @param token 用户登录token
     * @param input 更新的基础信息
     * @return 用户实体
     */
    User update(String token, UserInput input);

    /**
     * 删除用户(注销)
     *
     * @param token 用户token
     * @param id    用户id
     */
    void delete(String token, String id);

    /**
     * 上传头像
     *
     * @param token 用户token
     * @param file  头像文件
     */
    void uploadPortrait(String token, MultipartFile file);


    /**
     * 用户登录
     *
     * @param username 用户名
     * @param pwd      密码
     * @return 用户token
     */
    UserOutput login(String username, String pwd);

    /**
     * 用户退出登录
     *
     * @param token 用户token
     */
    void quit(String token);

    /**
     * 获取关注的用户
     *
     * @param token 用户token
     * @return 用户List
     */
    List<User> listFollow(String token);

    /**
     * 获取用户创建的话题
     *
     * @param token 用户token
     * @return 话题List
     */
    List<Subject> subjects(String token);

    /**
     * 获取用户关注的话题
     *
     * @param token 用户token
     * @return 话题列表
     */
    List<Subject> listConcern(String token);

    /**
     * 获取用户收藏的文章
     *
     * @param token 用户token
     * @return 文章列表
     */
    List<Article> listCollection(String token);

    /**
     * 通过token获取用户信息
     *
     * @param token 用户登录token
     * @return 用户信息
     */
    User getByToken(String token);


}