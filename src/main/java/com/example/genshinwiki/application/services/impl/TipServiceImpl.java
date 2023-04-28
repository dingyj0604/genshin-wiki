//package com.example.genshinwiki.application.services.impl;
//
//import cn.hutool.core.bean.BeanUtil;
//import com.example.genshinwiki.application.dto.input.ArticleInput;
//import com.example.genshinwiki.application.services.ArticleService;
//import com.example.genshinwiki.domain.abstracts.Article;
//import com.example.genshinwiki.domain.entity.Comment;
//import com.example.genshinwiki.domain.entity.Likes;
//import com.example.genshinwiki.domain.entity.Subject;
//import com.example.genshinwiki.domain.entity.User;
//import com.example.genshinwiki.domain.entity.article.Tip;
//import com.example.genshinwiki.domain.enums.ArticleStatus;
//import com.example.genshinwiki.domain.repository.*;
//import com.example.genshinwiki.infrastructure.model.PageInput;
//import com.example.genshinwiki.infrastructure.model.PageOutPut;
//import com.example.genshinwiki.infrastructure.util.PageInputHelper;
//import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.List;
//import java.util.stream.Collectors;
//
///**
// * @author zxd
// * @date 2022/6/7 9:49
// */
//@Service
//@AllArgsConstructor
//@Transactional(readOnly = true)
//public class TipServiceImpl implements ArticleService {
//    private final UserRepo userRepo;
//    private final SubjectRepo subjectRepo;
//    private final CommentRepo commentRepo;
//    private final ArticleRepo articleRepo;
//    private final LikesRepo likesRepo;
//
//    @Override
//    @Transactional(rollbackFor = Exception.class)
//    public Article insert(String userId, ArticleInput input) {
//        Article article = new Tip(userRepo.findByIdOrThrow(userId));
//        BeanUtil.copyProperties(input, article);
//        article.setStatus(ArticleStatus.Editing);
//        List<Subject> subjects =
//                input.getSubjectIds().stream()
//                        .map(subjectRepo::findByIdOrThrow).collect(Collectors.toList());
//        article.setSubjects(subjects);
//        return articleRepo.save(article);
//    }
//
//    @Override
//    public Article update(String id, ArticleInput input) {
//        Article article = articleRepo.findByIdOrThrow(id);
//        BeanUtil.copyProperties(input, article);
//        return articleRepo.save(article);
//    }
//
//    @Override
//    public void delete(String id) {
//        articleRepo.delete(articleRepo.findByIdOrThrow(id));
//    }
//
//    @Override
//    public Article findBy(String id) {
//        return articleRepo.findByIdOrThrow(id);
//    }
//
//    @Override
//    public List<Article> listBy(String userId) {
//        User user = userRepo.findByIdOrThrow(userId);
//        return articleRepo.findAllByAuthorIs(user);
//    }
//
//    @Override
//    public PageOutPut<Article> pageBy(PageInput pageInput) {
//        return PageOutPut.of(articleRepo.findAll(PageInputHelper.toPageable(pageInput)));
//    }
//
//    @Override
//    public Article release(String id) {
//        Article article = articleRepo.findByIdOrThrow(id);
//        article.setStatus(ArticleStatus.Reviewing);
//        return articleRepo.save(article);
//    }
//
//    @Override
//    public List<Article> listByExamine() {
//        return articleRepo.findAllByStatusIs(ArticleStatus.Reviewing);
//    }
//
//    @Override
//    public Article examine(String id, String status) {
//        Article article = articleRepo.findByIdOrThrow(id);
//        article.setStatus(ArticleStatus.valueOf(status));
//        return articleRepo.save(article);
//    }
//
//    @Override
//    public Article comment(String id, String userId, String context) {
//        User user = userRepo.findByIdOrThrow(userId);
//        Article article = articleRepo.findByIdOrThrow(id);
//        Comment comment = new Comment(user);
//        comment.setContext(context);
//        comment = commentRepo.save(comment);
//        article.getComments().add(comment);
//        return articleRepo.save(article);
//    }
//
//    @Override
//    public Article support(String id, String userId) {
//        User user = userRepo.findByIdOrThrow(userId);
//        Article article = articleRepo.findByIdOrThrow(id);
//        article.getLikes().add(likesRepo.save(new Likes(user)));
//        return articleRepo.save(article);
//    }
//}
