package com.example.genshinwiki.api.restful;

import cn.hutool.core.io.FileUtil;
import com.example.genshinwiki.application.services.UserService;
import com.example.genshinwiki.infrastructure.common.rest.R;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * @author zxd
 * @date 2022/6/15 18:53
 */
@RestController
@RequiredArgsConstructor
public class AppApi {
    public final UserService service;

    @GetMapping("/download")
    @ResponseBody
    public String download(@RequestParam String filepath, HttpServletResponse response) throws IOException {
        response.setContentType("application/force-download");
        response.setHeader("Content-Disposition", "attachment;fileName=" + FileUtil.getName(filepath));
        System.out.println("test");
        OutputStream outputStream = response.getOutputStream();
        FileInputStream inputStream = new FileInputStream(filepath);
        IOUtils.copy(inputStream, outputStream);
        outputStream.close();
        inputStream.close();
        return null;
    }
}
