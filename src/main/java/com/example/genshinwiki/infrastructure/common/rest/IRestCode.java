package com.example.genshinwiki.infrastructure.common.rest;

/**
 * 响应枚举接口
 *
 * @author ankelen
 * @date 2020-10-13 17:33
 */
public interface IRestCode {
    /**
     * 响应码
     *
     * @return int
     */
    int getCode();

    /**
     * 响应信息
     *
     * @return String
     */
    String getMsg();
}
