package com.example.genshinwiki.infrastructure.util;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Objects;

/**
 * @author zxd
 * @date 2022/6/9 16:47
 */
public class StringTemplateUtil {
    private static StringRedisTemplate template;
//    private static RedisConnectionFactory connectionFactory;
//    private static RedisConnection connection;

    public static StringRedisTemplate setTemplate() {
        template = new StringRedisTemplate();
        init();
        return template;
    }

    private static void init() {
//        connectionFactory = template.getConnectionFactory();
//        assert connectionFactory != null;
//        connection = connectionFactory.getConnection();
    }

    public static void saveValue(String key, String value) {
        if (Objects.isNull(template)) {
            setTemplate();
        }
        template.opsForValue().set(key, value);
    }

    public static String getValue(String key) {
        return template.opsForValue().get(key);
    }
}
