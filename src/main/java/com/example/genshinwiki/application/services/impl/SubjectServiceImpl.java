package com.example.genshinwiki.application.services.impl;

import cn.hutool.core.bean.BeanUtil;
import com.example.genshinwiki.application.dto.input.SubjectInput;
import com.example.genshinwiki.application.services.SubjectService;
import com.example.genshinwiki.domain.entity.Subject;
import com.example.genshinwiki.domain.repository.SubjectRepo;
import com.example.genshinwiki.domain.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author zxd
 * @date 2022/6/6 9:50
 */
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class SubjectServiceImpl implements SubjectService {

    private final SubjectRepo repo;
    private final UserRepo userRepo;
    private final StringRedisTemplate template;

    @Override
    public Subject getBy(String id) {
        return repo.findByIdOrThrow(id);
    }

    //通过关键字查找(模糊查询)
    @Override
    public List<Subject> listByKeyword(String keyword) {
        return repo.findAllByTitleIsLike("%" + keyword + "%");
    }

    @Override
    public List<Subject> listByUser(String userId) {
        return repo.findAllByAuthorIs(userRepo.findByIdOrThrow(userId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Subject insert(String token, SubjectInput input) {
        Subject subject = new Subject();
        BeanUtil.copyProperties(input, subject);
        subject.setAuthor(userRepo.findByIdOrThrow(template.opsForValue().get(token)));
        return repo.save(subject);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Subject update(String token, String id, SubjectInput input) {
        Subject subject = repo.findByIdOrThrow(id);
        subject.setUpdateBy(template.opsForValue().get(token));
        BeanUtil.copyProperties(input, subject);
        return repo.save(subject);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(String token, String id) {
        repo.delete(repo.findByIdOrThrow(id));
    }

    @Override
    public List<Subject> listBy() {
        return repo.findAll();
    }

    //获取热门话题
    @Override
    public List<Subject> getHots() {
        int limit = 5;
        List<Subject> subjects = repo.findAllByOrderByDiscussionDesc();
        if (subjects.size() > limit) {
            return subjects.subList(0, limit);
        } else {
            return subjects;
        }
    }
}
