package com.example.genshinwiki.application.services.impl;

import com.example.genshinwiki.annotation.UserVerify;
import com.example.genshinwiki.application.services.CommentService;
import com.example.genshinwiki.domain.entity.Comment;
import com.example.genshinwiki.domain.entity.Likes;
import com.example.genshinwiki.domain.entity.User;
import com.example.genshinwiki.domain.repository.CommentRepo;
import com.example.genshinwiki.domain.repository.LikesRepo;
import com.example.genshinwiki.domain.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author zxd
 * @date 2022/6/6 17:06
 */
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class CommentServiceImpl implements CommentService {
    private LikesRepo likesRepo;
    private UserRepo userRepo;
    private CommentRepo commentRepo;
    private StringRedisTemplate template;


    @Override
    @UserVerify
    @Transactional(rollbackFor = Exception.class)
    public Comment reply(String token, String id, String context) {
        User user = userRepo.findByIdOrThrow(template.opsForValue().get(token));
        Comment replied = commentRepo.findByIdOrThrow(id);
        Comment reply = new Comment(user);
        reply.setReplyTo(replied);
        reply.setContext(context);
        return commentRepo.save(reply);
    }

    @Override
    @UserVerify
    @Transactional(rollbackFor = Exception.class)
    public Comment support(String token, String id) {
        User user = userRepo.findByIdOrThrow(template.opsForValue().get(token));
        Comment comment = commentRepo.findByIdOrThrow(id);
        comment.getLikes().add(likesRepo.save(new Likes(user)));
        return commentRepo.save(comment);
    }
}
