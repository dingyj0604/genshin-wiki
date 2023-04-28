package com.example.genshinwiki.application.dto.value;

import cn.hutool.core.io.FileUtil;
import cn.hutool.json.JSONUtil;
import com.example.genshinwiki.infrastructure.common.support.SnowflakeIdGenerator;
import com.example.genshinwiki.infrastructure.consts.AppConst;
import com.example.genshinwiki.infrastructure.util.StringPool;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zxd
 * @date 2022/6/14 10:05
 */
@Data
public class TrendValue {
    String content;
    List<String> images;

    public String toJson() {
        return JSONUtil.toJsonStr(this);
    }

    public static TrendValue toBean(String str) {
        return JSONUtil.toBean(str, TrendValue.class);
    }

    public TrendValue() {

    }

    public TrendValue(String content, List<MultipartFile> files) {
        this.content = content;
        this.setImages(files.stream().map(
                file -> {
                    String filename = Paths.get(
                            AppConst.Dir.USER_DIR,
                            AppConst.Dir.IMG_DIR,
                            SnowflakeIdGenerator.SNOWFLAKE.nextIdStr(),
                            StringPool.DOT,
                            FileUtil.getSuffix(file.getOriginalFilename())).toString();
                    try {
                        file.transferTo(Paths.get(filename));
                        return filename;
                    } catch (IOException e) {
                        throw new RuntimeException("图片上传失败!");
                    }
                }).collect(Collectors.toList()));
    }

}
