package com.example.genshinwiki.annotation;

/**
 * @author zxd
 * @date 2022/6/12 21:42
 */
public @interface AdminVerify {
    String value() default "ROLE_ADMIN";
}
