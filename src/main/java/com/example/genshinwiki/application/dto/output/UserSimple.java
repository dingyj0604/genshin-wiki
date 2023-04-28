package com.example.genshinwiki.application.dto.output;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.io.FileUtil;
import com.example.genshinwiki.domain.entity.User;
import com.example.genshinwiki.domain.enums.Role;
import com.example.genshinwiki.infrastructure.consts.AppConst;
import lombok.Data;

import java.nio.file.Paths;

/**
 * @author zxd
 * @date 2022/6/15 22:01
 */
@Data
public class UserSimple {
    private String username;
    private String name;
    private String autograph;
    private String portrait;
    private Role role;

    public String getPortraitPath() {
        return Paths.get(AppConst.Dir.IMG_ROOT_PATH, FileUtil.getName(this.portrait)).toString();
    }

    public static UserSimple of(User user) {
        UserSimple simple = new UserSimple();
        BeanUtil.copyProperties(user, simple);
        return simple;
    }
}
