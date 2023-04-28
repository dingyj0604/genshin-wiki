package com.example.genshinwiki.application.dto.output;

import com.example.genshinwiki.domain.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author zxd
 * @date 2022/6/12 14:42
 */
@Data
@NoArgsConstructor
public class UserOutput {
    private String token;
    private User user;

    public UserOutput(String token, User user) {
        this.user = user;
        this.token = token;
    }
}
