package com.example.genshinwiki.config;

import com.example.genshinwiki.infrastructure.common.rest.R;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author ankelen
 * @date 2022-01-10 15:23
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public R<?> onException(Exception e) {
        e.printStackTrace();
        return R.fail(e.getMessage());
    }
}
