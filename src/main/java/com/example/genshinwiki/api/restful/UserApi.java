package com.example.genshinwiki.api.restful;

import com.example.genshinwiki.application.dto.input.UserInput;
import com.example.genshinwiki.application.services.UserService;
import com.example.genshinwiki.infrastructure.common.rest.R;
import lombok.AllArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户接口
 *
 * @author zxd
 * @date 2022/6/6 8:54
 */
@RestController
@AllArgsConstructor
@RequestMapping("/user")
public class UserApi {
    public final UserService service;

    @PostMapping("/login")
    public R<?> login(@Param("username") String username, @Param("password") String password) {
        return R.data(service.login(username, password));
    }



    @GetMapping("/{id}")
    public R<?> getUserBy(@PathVariable String id) {
        return R.data(service.getBy(id));
    }

    @GetMapping("")
    public R<?> getUserBy(HttpServletRequest request) {
        return R.data(service.getByToken(request.getHeader("token")));
    }

    @PostMapping("")
    public R<?> insert(@Param("username") String username, @Param("password") String password) {
        return R.data(service.insert(username, password));
    }

    @PutMapping("")
    public R<?> update(@RequestBody UserInput input, HttpServletRequest request) {
        String token = request.getHeader("token");
        return R.data(service.update(token, input));
    }

    @DeleteMapping("/{id}/{token}")
    public R<?> delete(@PathVariable String token, @PathVariable String id) {
        service.delete(token, id);
        return R.ok("删除成功");
    }

    @DeleteMapping("/{token}")
    public R<?> quit(@PathVariable String token) {
        service.quit(token);
        return R.ok("退出登录成功");
    }
    @GetMapping("/works")
    public R<?> works(HttpServletRequest request){
        return R.data(service.works(request.getHeader("token")));
    }


    @GetMapping("/follows")
    public R<?> listFollowBy(HttpServletRequest request) {
        return R.data(service.listFollow(request.getHeader("token")));
    }

    @PutMapping("/follow")
    public R<?> follow(@RequestParam String followId, HttpServletRequest request) {
        return R.data(service.follow(request.getHeader("token"), followId));
    }
    @PutMapping("/un-follow")
    public R<?> unFollow(@RequestParam String followId, HttpServletRequest request) {
        return R.data(service.unFollow(request.getHeader("token"), followId));
    }

    @PutMapping("/concern")
    public R<?> concern(HttpServletRequest request, @RequestParam String concernId) {
        return R.data(service.concern(request.getHeader("token"), concernId));
    }

    @GetMapping("/concerns")
    public R<?> listConcernBy(HttpServletRequest request) {
        return R.data(service.listConcern(request.getHeader("token")));
    }

    @GetMapping("/collections")
    public R<?> litCollectionBy(HttpServletRequest request) {
        return R.data(service.listCollection(request.getHeader("token")));
    }

    @PostMapping("/upload-portrait")
    public R<?> uploadPortrait(HttpServletRequest request, @RequestBody MultipartFile file) {
        service.uploadPortrait(request.getHeader("token"), file);
        return R.ok();
    }

    @GetMapping("/subjects")
    public R<?> subjects(HttpServletRequest request) {
        return R.data(service.subjects(request.getHeader("token")));
    }
}
