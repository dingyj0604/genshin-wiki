package com.example.genshinwiki.application.dto.input;


import lombok.Data;

/**
 * @author zxd
 * @date 2022/6/5 22:08
 */
@Data
public class UserInput {
    /**
     * 昵称
     */
    private String name;
    /**
     * 签名
     */
    private String autograph;
}
