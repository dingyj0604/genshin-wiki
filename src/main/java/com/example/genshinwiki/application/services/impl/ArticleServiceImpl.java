package com.example.genshinwiki.application.services.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.genshinwiki.annotation.AdminVerify;
import com.example.genshinwiki.annotation.UserVerify;
import com.example.genshinwiki.application.dto.input.ArticleInput;
import com.example.genshinwiki.application.dto.output.ArticleSimple;
import com.example.genshinwiki.application.services.ArticleService;
import com.example.genshinwiki.domain.abstracts.Article;
import com.example.genshinwiki.domain.entity.Comment;
import com.example.genshinwiki.domain.entity.Likes;
import com.example.genshinwiki.domain.entity.Subject;
import com.example.genshinwiki.domain.entity.User;
import com.example.genshinwiki.domain.entity.article.Tip;
import com.example.genshinwiki.domain.enums.ArticleStatus;
import com.example.genshinwiki.domain.enums.ArticleType;
import com.example.genshinwiki.domain.repository.*;
import com.example.genshinwiki.infrastructure.model.PageInput;
import com.example.genshinwiki.infrastructure.model.PageOutPut;
import com.example.genshinwiki.infrastructure.util.PageInputHelper;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zxd
 * @date 2022/6/7 9:49
 */
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class ArticleServiceImpl implements ArticleService {
    private final UserRepo userRepo;
    private final SubjectRepo subjectRepo;
    private final CommentRepo commentRepo;
    private final ArticleRepo articleRepo;
    private final LikesRepo likesRepo;
    private final StringRedisTemplate template;

    @Override
    @UserVerify
    @Transactional(rollbackFor = Exception.class)
    public ArticleSimple insertTip(String token, ArticleInput input) {
        Article article = new Tip(userRepo.findByIdOrThrow(template.opsForValue().get(token)));
        BeanUtil.copyProperties(input, article);
        article.setStatus(ArticleStatus.Editing);
        List<Subject> subjects =
                input.getSubjectIds().stream()
                        .map(subjectRepo::findByIdOrThrow).collect(Collectors.toList());
        subjects.forEach(subject -> subject.setDiscussion(subject.getDiscussion() + 1));
        article.setSubjects(subjects);
        return new ArticleSimple(articleRepo.save(article));
    }

    @Override
    @UserVerify
    @Transactional(rollbackFor = Exception.class)
    public ArticleSimple insertTrend(String token, ArticleInput input) {
        String userId = template.opsForValue().get(token);
        Article article = ArticleType.TREND.apply(userRepo.findByIdOrThrow(userId));
        BeanUtil.copyProperties(input, article);
//        TrendValue value = new TrendValue(input.getContent(), );
        article.setContent(input.getContent());
        if (input.getSubjectIds().size() > 0) {
            List<Subject> subjects =
                    input.getSubjectIds().stream()
                            .map(subjectRepo::findByIdOrThrow).collect(Collectors.toList());
            subjects.forEach(subject -> subject.setDiscussion(subject.getDiscussion() + 1));
        }
        return new ArticleSimple(articleRepo.save(article));
    }

    @Override
    @UserVerify
    @Transactional(rollbackFor = Exception.class)
    public ArticleSimple update(String token, String id, ArticleInput input) {
        User user = userRepo.findByIdOrThrow(template.opsForValue().get(token));
        Article article = articleRepo.findByIdOrThrow(id);
        if (!user.getId().equals(article.getAuthor().getId())) {
            throw new RuntimeException("更新失败!");
        }
        BeanUtil.copyProperties(input, article);
        article.setStatus(ArticleStatus.Editing);
        return new ArticleSimple(articleRepo.save(article));
    }

    @Override
    @UserVerify
    @Transactional(rollbackFor = Exception.class)
    public void delete(String token, String id) {
        User user = userRepo.findByIdOrThrow(template.opsForValue().get(token));
        Article article = articleRepo.findByIdOrThrow(id);
        if (!user.getId().equals(article.getAuthor().getId())) {
            throw new RuntimeException("删除失败!");
        }
        articleRepo.delete(article);
    }

    @Override
    public Article findBy(String id) {
        Article article = articleRepo.findByIdOrThrow(id);
        article.setReadingVolume(article.getReadingVolume() + 1);
        return articleRepo.save(article);
    }

    @Override
    public PageOutPut<Article> pageBy(PageInput pageInput) {
        return PageOutPut.of(articleRepo.findAll(PageInputHelper.toPageable(pageInput)));
    }

    @Override
    @UserVerify
    public ArticleSimple release(String token, String id) {
        Article article = articleRepo.findByIdOrThrow(id);
        if (!article.getAuthor().getId().equals(template.opsForValue().get(token))) {
            throw new RuntimeException("发布失败!");
        }
        article.setStatus(ArticleStatus.Reviewing);
        return new ArticleSimple(articleRepo.save(article));
    }

    @Override
    @AdminVerify
    public List<ArticleSimple> listByExamine(String token) {
        return ArticleSimple.toSimples(articleRepo.findAllByStatusIs(ArticleStatus.Reviewing));
    }

    // 审核文章实现
    @Override
    @AdminVerify
    public Article examine(String token, String id, String status) {
        Article article = articleRepo.findByIdOrThrow(id);
        article.setStatus(ArticleStatus.valueOf(status));
        return articleRepo.save(article);
    }

    @Override
    @UserVerify
    public Article comment(String token, String id, String context) {
        User user = userRepo.findByIdOrThrow(template.opsForValue().get(token));
        Article article = articleRepo.findByIdOrThrow(id);
        Comment comment = new Comment(user);
        comment.setContext(context);
        comment = commentRepo.save(comment);
        article.getComments().add(comment);
        return articleRepo.save(article);
    }

    //点赞实现
    @Override
    @UserVerify
    @Transactional(rollbackFor = Exception.class)
    public ArticleSimple support(String token, String id) {
        User user = userRepo.findByIdOrThrow(template.opsForValue().get(token));
        Article article = articleRepo.findByIdOrThrow(id);
        article.getLikes().add(likesRepo.save(new Likes(user)));
        return new ArticleSimple(articleRepo.save(article));
    }

    @Override
    @UserVerify
    public ArticleSimple unSupport(String token, String id) {
        User user = userRepo.findByIdOrThrow(template.opsForValue().get(token));
        Article article = articleRepo.findByIdOrThrow(id);
        List<Likes> likesList = article.getLikes()
                .stream()
                .filter(likes -> likes.getUserId().equals(user.getId()))
                .collect(Collectors.toList());
        article.getLikes().removeAll(likesList);
        return new ArticleSimple(articleRepo.save(article));
    }

    @Override
    @AdminVerify
    @Transactional(rollbackFor = Exception.class)
    public Article setTop(String token, String id) {
        Article article = articleRepo.findByIdOrThrow(id);
        article.setTopping(!article.isTopping());
        return articleRepo.save(article);
    }

    @Override
    public List<ArticleSimple> getTops() {
        return ArticleSimple.toSimples(articleRepo.findAllByToppingIsAndStatusIs(true, ArticleStatus.Published));
    }

    @Override
    @UserVerify
    public ArticleSimple collection(String token, String id) {
        User user = userRepo.findByIdOrThrow(template.opsForValue().get(token));
        Article article = articleRepo.findByIdOrThrow(id);
        user.getCollections().add(article);
        userRepo.save(user);
        return new ArticleSimple(articleRepo.save(article));
    }

    @Override
    @UserVerify
    public ArticleSimple unCollection(String token, String id) {
        User user = userRepo.findByIdOrThrow(template.opsForValue().get(token));
        Article article = articleRepo.findByIdOrThrow(id);
        user.getCollections().remove(article);
        userRepo.save(user);
        return new ArticleSimple(articleRepo.save(article));
    }

    //获取文章列表实现
    @Override
    public List<ArticleSimple> list() {
        return ArticleSimple.toSimples(articleRepo.findAll());
    }

    @Override
    public List<ArticleSimple> listBy(String type) {
        return ArticleSimple.toSimples(articleRepo.findAllByTypeIsAndStatusIs(ArticleType.valueOf(type.toUpperCase()), ArticleStatus.Published));
    }

    @Override
    public List<ArticleSimple> listBySubject(String subjectId) {
        return ArticleSimple.toSimples(articleRepo.findAllBySubjectAndStatusIs(ArticleStatus.Published.toString(), subjectId));
    }


}
