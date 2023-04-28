package com.example.genshinwiki.config;

import com.example.genshinwiki.infrastructure.consts.AppConst;
import com.example.genshinwiki.infrastructure.util.IocUtil;
import com.example.genshinwiki.infrastructure.util.StringPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author zxd
 * @date 2022/6/2 16:00
 */
@Configuration(proxyBeanMethods = false)
public class AppBeanConfig {
    /**
     * IoC工具类
     */
    @Bean
    @Lazy(value = false)
    public IocUtil iocUtil() {
        return IocUtil.getInstance();
    }

    /**
     * WebMVC配置Bean
     */
    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("*")
                        .allowedOrigins("*");
            }

            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/**")
                        .addResourceLocations("file:"+ AppConst.Dir.USER_DIR+AppConst.Dir.IMG_DIR)
                        .addResourceLocations("classpath:/META-INF/resources/");
            }
        };
    }
//    /**
//     * 自定义Jackson配置
//     * 为所有的关联字段加上@JsonIgnore
//     */
//    @Configuration(proxyBeanMethods = false)
//    static class JacksonCustomizerConfig {
//        @JsonIgnoreType
//        static class MyMixInForIgnoreType {
//        }
//
//        @Bean
//        public Jackson2ObjectMapperBuilderCustomizer customizer() {
//            return builder -> builder.mixIn(BaseEntity.class, MyMixInForIgnoreType.class);
//        }
//    }
}
