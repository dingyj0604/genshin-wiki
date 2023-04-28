package com.example.genshinwiki.api.restful;

import com.example.genshinwiki.application.dto.input.ArticleInput;
import com.example.genshinwiki.application.services.ArticleService;
import com.example.genshinwiki.infrastructure.common.rest.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Objects;

/**
 * 文章接口
 * TODO 动态和攻略区分
 *
 * @author zxd
 * @date 2022/6/7 20:11
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleApi {
    private final ArticleService service;

    @PostMapping("/tip")
    public R<?> insertTip(@RequestBody ArticleInput input, HttpServletRequest request) {
        return R.data(service.insertTip(request.getHeader("token"), input));
    }

    @PostMapping("/trend")
    public R<?> insertTrend(@RequestBody ArticleInput input, HttpServletRequest request) {
        return R.data(service.insertTrend(request.getHeader("token"), input));
    }

    @PutMapping("/{id}")
    public R<?> update(@PathVariable String id, @RequestBody ArticleInput input, HttpServletRequest request) {
        return R.data(service.update(request.getHeader("token"), id, input));
    }

    @DeleteMapping("/{id}")
    public R<?> delete(@PathVariable String id, HttpServletRequest request) {
        service.delete(request.getHeader("token"), id);
        return R.ok("删除成功!");
    }

    @GetMapping("/{id}")
    public R<?> findBy(@PathVariable String id) {
        return R.data(service.findBy(id));
    }

    @GetMapping("/list")
    public R<?> listBy(HttpServletRequest request) {
        String token = request.getHeader("token");
        if (Objects.isNull(token)) {
            return R.data(service.list());
        } else {
            return R.data(token);
        }

    }

    @GetMapping("/list/{type}")
    public R<?> listBy(@PathVariable String type) {
        return R.data(service.listBy(type));
    }


    @GetMapping("/list/subject")
    public R<?> listBySubject(@RequestParam String subjectId) {
        return R.data(service.listBySubject(subjectId));
    }


    @PutMapping("/{id}/release")
    public R<?> release(HttpServletRequest request, @PathVariable String id) {
        return R.data(service.release(request.getHeader("token"), id));
    }

    @GetMapping("/examine-list")
    public R<?> listByExamine(HttpServletRequest request) {
        return R.data(service.listByExamine(request.getHeader("token")));
    }

    @PutMapping("/{id}/examine")
    public R<?> examine(@PathVariable String id, @RequestParam String status,HttpServletRequest request) {
        return R.data(service.examine(request.getHeader("token"),id, status));
    }

    @PutMapping("/{id}/comment")
    public R<?> comment(HttpServletRequest request, @PathVariable String id, @RequestBody String context) {
        return R.data(service.comment(request.getHeader("token"), id, context));
    }

    @PutMapping("/{id}/support")
    public R<?> support(HttpServletRequest request, @PathVariable String id) {
        return R.data(service.support(request.getHeader("token"), id));
    }

    @PutMapping("/{id}/un-support")
    public R<?> unSupport(HttpServletRequest request, @PathVariable String id) {
        return R.data(service.unSupport(request.getHeader("token"), id));
    }

    @PutMapping("/{id}/top")
    public R<?> toTop(HttpServletRequest request, @PathVariable String id) {
        return R.data(service.setTop(request.getHeader("token"), id));
    }

    @GetMapping("/tops")
    public R<?> tops() {
        return R.data(service.getTops());
    }

    @PostMapping("/{id}/collection")
    public R<?> collection(@PathVariable String id, HttpServletRequest request) {
        return R.data(service.collection(request.getHeader("token"), id));
    }

    @PostMapping("/{id}/un-collection")
    public R<?> unCollection(@PathVariable String id, HttpServletRequest request) {
        return R.data(service.unCollection(request.getHeader("token"), id));
    }
}
