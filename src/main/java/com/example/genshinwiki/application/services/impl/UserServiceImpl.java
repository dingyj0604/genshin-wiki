package com.example.genshinwiki.application.services.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.crypto.SecureUtil;
import com.example.genshinwiki.annotation.UserVerify;
import com.example.genshinwiki.application.dto.input.UserInput;
import com.example.genshinwiki.application.dto.output.ArticleSimple;
import com.example.genshinwiki.application.dto.output.UserOutput;
import com.example.genshinwiki.application.services.UserService;
import com.example.genshinwiki.domain.abstracts.Article;
import com.example.genshinwiki.domain.entity.Subject;
import com.example.genshinwiki.domain.entity.User;
import com.example.genshinwiki.domain.enums.Role;
import com.example.genshinwiki.domain.repository.ArticleRepo;
import com.example.genshinwiki.domain.repository.SubjectRepo;
import com.example.genshinwiki.domain.repository.UserRepo;
import com.example.genshinwiki.infrastructure.common.support.SnowflakeIdGenerator;
import com.example.genshinwiki.infrastructure.consts.AppConst;
import com.example.genshinwiki.infrastructure.util.StringPool;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author zxd
 * @date 2022/6/5 22:11
 */
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private UserRepo repo;
    private SubjectRepo subjectRepo;
    private ArticleRepo articleRepo;
    private StringRedisTemplate template;

    // 创建用户 (注册）
    @Override
    @Transactional(rollbackFor = Exception.class)
    public User insert(String username, String pwd) {
        if (!Objects.equals(repo.findUserByUsernameIs(username), null)) {
            throw new RuntimeException("该用户已存在!");
        }
        User user = new User(Role.ROLE_USER);
        user.setUsername(username);
        user.setPassword(SecureUtil.md5(pwd));
        user.setName(AppConst.APP_NAME + SnowflakeIdGenerator.SNOWFLAKE.nextIdStr().substring(10));
        return repo.save(user);
    }

    // 更新用户信息
    @Override
    @UserVerify
    @Transactional(rollbackFor = Exception.class)
    public User update(String token, UserInput input) {
        String id = template.opsForValue().get(token);
        User user = getBy(id);
        BeanUtil.copyProperties(input, user);
        return repo.save(user);
    }

    @Override
    @UserVerify
    @Transactional(rollbackFor = Exception.class)
    public void delete(String token, String id) {
        String userId = template.opsForValue().get(token);
        if (!id.equals(userId)) {
            throw new RuntimeException("用户id与登录用户不匹配!");
        }
        template.delete(token);
        repo.delete(getBy(id));
    }

    @Override
    @UserVerify
    @Transactional(rollbackFor = Exception.class)
    public void uploadPortrait(String token, MultipartFile file) {
        User user = repo.findByIdOrThrow(template.opsForValue().get(token));
        String filename = SnowflakeIdGenerator.SNOWFLAKE.nextIdStr() + StringPool.DOT + FileUtil.getSuffix(file.getOriginalFilename());
        if (!Objects.isNull(user.getPortrait())) {
            FileUtil.del(user.getPortrait());
        }
        user.setPortrait(Paths.get(AppConst.Dir.USER_DIR, AppConst.Dir.IMG_DIR, filename).toString());
        try {
            file.transferTo(FileUtil.newFile(user.getPortrait()));
            repo.save(user);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 关注 用户
    @Override
    @UserVerify
    @Transactional(rollbackFor = Exception.class)
    public User follow(String token, String followedId) {
        User user = repo.findByIdOrThrow(template.opsForValue().get(token));
        User followed = repo.findByIdOrThrow(followedId);
        List<User> follows = user.getFollows();
        if (!follows.contains(followed)) {
            follows.add(followed);
        }
        user.setFollows(follows);
        return repo.save(user);
    }

    @Override
    @UserVerify
    @Transactional(rollbackFor = Exception.class)
    public User unFollow(String token, String followedId) {
        User user = repo.findByIdOrThrow(template.opsForValue().get(token));
        User followed = repo.findByIdOrThrow(followedId);
        List<User> follows = user.getFollows();
        follows.remove(followed);
        user.setFollows(follows);
        return repo.save(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public User concern(String token, String concernedId) {
        User user = repo.findByIdOrThrow(template.opsForValue().get(token));
        Subject subject = subjectRepo.findByIdOrThrow(concernedId);
        List<Subject> concerns = user.getConcerns();
        boolean b = (concerns.contains(subject)) ? (concerns.remove(subject)) : (concerns.add(subject));
        user.setConcerns(concerns);
        return repo.save(user);
    }

    @Override
    public User collect(String id, String collectionId) {
        User user = repo.findByIdOrThrow(id);
        Article article = articleRepo.findByIdOrThrow(collectionId);
        List<Article> collections = user.getCollections();
        boolean b = collections.contains(article) ? collections.remove(article) : collections.add(article);
        user.setCollections(collections);
        return repo.save(user);
    }

    @Override
    public List<ArticleSimple> works(String token) {
        return ArticleSimple.toSimples(articleRepo.findAllByAuthorIs(repo.findByIdOrThrow(template.opsForValue().get(token))));
    }

    public User getBy(String id) {
        return repo.findByIdOrThrow(id);
    }

    // 用户登录
    @Override
    public UserOutput login(String username, String pwd) {
        User user = repo.findUserByUsernameIs(username);
        if (Objects.isNull(user)) {
            throw new RuntimeException("用户名不存在!");
        } else if (!Objects.equals(SecureUtil.md5(pwd), user.getPassword())) {
            throw new RuntimeException("用户密码错误");
        }
        String token = SnowflakeIdGenerator.SNOWFLAKE.nextIdStr();
        template.opsForValue().set(token, user.getId(), 60 * 60, TimeUnit.SECONDS);
        return new UserOutput(token, user);
    }

    @Override
    public void quit(String token) {
        template.delete(token);
    }

    @Override
    @UserVerify
    public List<User> listFollow(String token) {
        return repo.findByIdOrThrow(template.opsForValue().get(token)).getFollows();
    }

    @Override
    public List<Subject> subjects(String token) {
        User user = repo.findByIdOrThrow(template.opsForValue().get(token));
        return subjectRepo.findAllByAuthorIs(user);
    }

    @Override
    @UserVerify
    public List<Subject> listConcern(String token) {
        return repo.findByIdOrThrow(template.opsForValue().get(token)).getConcerns();
    }

    @Override
    @UserVerify
    public List<Article> listCollection(String token) {
        return repo.findByIdOrThrow(template.opsForValue().get(token)).getCollections();
    }

    @Override
    @UserVerify
    public User getByToken(String token) {
        return repo.findByIdOrThrow(template.opsForValue().get(token));
    }

}
