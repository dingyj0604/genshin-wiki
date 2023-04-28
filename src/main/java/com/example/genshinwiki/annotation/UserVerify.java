package com.example.genshinwiki.annotation;

/**
 * 用户验证
 *
 * @author zxd
 * @date 2022/6/9 16:13
 */
public @interface UserVerify {
    String value() default "ROLE_USER";
}
