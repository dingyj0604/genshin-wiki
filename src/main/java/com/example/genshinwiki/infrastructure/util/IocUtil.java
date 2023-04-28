package com.example.genshinwiki.infrastructure.util;

import org.springframework.context.ApplicationContext;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContextAware;

/**
 * @author zxd
 * @date 2022/6/4 16:37
 */
@SuppressWarnings({"unchecked"})
public class IocUtil implements ApplicationContextAware {
    private static ApplicationContext ctx;

    public static <T> T get(Class<T> clz) {
        return ctx.getBean(clz);
    }

    public static <T> T get(String name) {
        return ctx.containsBean(name) ? (T) ctx.getBean(name) : null;
    }

    public static <T> T get(String name, Class<T> clz) {
        return ctx.containsBean(name) ? ctx.getBean(name, clz) : null;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        IocUtil.ctx = applicationContext;
    }

    /**
     * 获取容器上下文
     */
    public static ApplicationContext getCtx() {
        return ctx;
    }

    /**
     * 获取SpringUtil 唯一实例
     */
    public static IocUtil getInstance() {
        return IocUtil.INSTANCE;
    }

    private static final IocUtil INSTANCE = new IocUtil();

    private IocUtil() {
    }
}
