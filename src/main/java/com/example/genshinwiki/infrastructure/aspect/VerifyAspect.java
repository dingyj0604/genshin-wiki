package com.example.genshinwiki.infrastructure.aspect;

import com.example.genshinwiki.domain.enums.Role;
import com.example.genshinwiki.domain.repository.UserRepo;
import lombok.AllArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * @author zxd
 * @date 2022/6/9 16:17
 */
@Aspect
@Component
@AllArgsConstructor
public class VerifyAspect {
    private StringRedisTemplate template;
    private UserRepo userRepo;

    @Pointcut("@annotation(com.example.genshinwiki.annotation.UserVerify)")
    private void pointcut() {
    }

    @Pointcut("@annotation(com.example.genshinwiki.annotation.AdminVerify)")
    private void pointcut2() {
    }

    @Around("pointcut()")
    public Object advice(ProceedingJoinPoint joinPoint) {
        String token = joinPoint.getArgs()[0].toString();
        String userId = template.opsForValue().get(token);
        if (Objects.isNull(userId) || Objects.isNull(userRepo.findByIdOrThrow(userId))) {
            throw new RuntimeException("用户未登录或登录失效!");
        }
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Around("pointcut2()")
    public Object adminAdvice(ProceedingJoinPoint joinPoint) {
        String token = joinPoint.getArgs()[0].toString();

        String userId = template.opsForValue().get(token);
        if (Objects.isNull(userId) || Objects.isNull(userRepo.findByIdOrThrow(userId))) {
            throw new RuntimeException("用户未登录或登录失效!");
        } else if (!userRepo.findByIdOrThrow(userId).getRole().equals(Role.ROLE_ADMIN)) {
            throw new RuntimeException("用户权限不够!");
        }
        try {
            return joinPoint.proceed();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

}
