package com.example.genshinwiki.api.restful;

import com.example.genshinwiki.application.dto.input.SubjectInput;
import com.example.genshinwiki.application.services.SubjectService;
import com.example.genshinwiki.infrastructure.common.rest.R;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 话题接口
 * TODO接口权限分离
 *
 * @author zxd
 * @date 2022/6/6 9:17
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/subject")
public class SubjectApi {
    private final SubjectService service;

    @GetMapping("/list-keyword")
    public R<?> listBy(@RequestParam String keyword) {
        return R.data(service.listByKeyword(keyword));
    }

    @GetMapping("/list")
    public R<?> listBy() {
        return R.data(service.listBy());
    }

    @PostMapping("")
    public R<?> insert(HttpServletRequest request, @RequestBody SubjectInput input) {
        return R.data(service.insert(request.getHeader("token"), input));
    }

    @PutMapping("/{id}")
    public R<?> update(HttpServletRequest request, @PathVariable String id, @RequestBody SubjectInput input) {
        return R.data(service.update(request.getHeader("token"), id, input));
    }

    @DeleteMapping("/{id}")
    public R<?> delete(HttpServletRequest request, @PathVariable String id) {
        service.delete(request.getHeader("token"), id);
        return R.ok("删除成功");
    }

    @GetMapping("/{id}")
    public R<?> getBy(@PathVariable String id) {
        return R.data(service.getBy(id));
    }

    @GetMapping("/hots")
    public R<?> getHots() {
        return R.data(service.getHots());
    }

}
