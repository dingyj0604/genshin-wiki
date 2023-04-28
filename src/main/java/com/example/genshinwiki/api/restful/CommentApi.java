package com.example.genshinwiki.api.restful;

import com.example.genshinwiki.application.services.CommentService;
import com.example.genshinwiki.infrastructure.common.rest.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zxd
 * @date 2022/6/7 20:43
 */

@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentApi {
    private final CommentService service;

    @PostMapping("/{id}/reply")
    public R<?> reply(@PathVariable String id, HttpServletRequest request, @RequestParam String context) {
        return R.data(service.reply(request.getHeader("token"),id, context));
    }

    @PutMapping("/{id}/support")
    public R<?> support(@PathVariable String id, HttpServletRequest request) {
        return R.data(service.support(request.getHeader("token"),id));
    }
}
