package com.example.genshinwiki.infrastructure.common.rest;

import java.io.Serializable;

/**
 * REST 响应封装
 *
 * @author ankelen
 * @date 2020-10-13 15:35
 */
public class R<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    protected R() {
    }

    private R(int code, String msg, T data, boolean success) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.success = success;
    }

    private R(int code, String msg, T data) {
        this(code, msg, data, (RestCode.SUCCESS.code == code));
    }

    private int code;
    private String msg;
    private T data;
    private boolean success;


    //region data

    /**
     * {code:200, msg:操作成功, data:data, success:true}
     */
    public static <T> R<T> data(T data) {
        return new R<>(RestCode.SUCCESS.code, RestCode.SUCCESS.msg, data);
    }

    /**
     * {code:200, msg:自定义msg, data:data, success:true}
     */
    public static <T> R<T> data(T data, String msg) {
        return new R<>(RestCode.SUCCESS.code, msg, data);
    }

    /**
     * {code:自定义code, msg:自定义msg, data:data, success:true}
     */
    public static <T> R<T> data(T data, int code, String msg) {
        return new R<>(code, msg, data, true);
    }

    //endregion


    //region ok

    /**
     * {code:200, msg:操作成功, data:null, success:true}
     */
    public static R<?> ok() {
        return new R<>(RestCode.SUCCESS.code, RestCode.SUCCESS.msg, null);
    }

    /**
     * {code:200, msg:自定义msg, data:null, success:true}
     */
    public static R<?> ok(String msg) {
        return new R<>(RestCode.SUCCESS.code, msg, null);
    }

    /**
     * {code:rc.code, msg:rc.msg, data:null, success:true}
     */
    public static R<?> ok(IRestCode rc) {
        return new R<>(rc.getCode(), rc.getMsg(), null, true);
    }

    //endregion

    //region fail

    /**
     * {code:400, msg:操作失败, data:null, success:false}
     */
    public static R<?> fail() {
        return new R<>(RestCode.FAILURE.code, RestCode.FAILURE.msg, null);
    }

    /**
     * {code:400, msg:自定义msg, data:null, success:false}
     */
    public static R<?> fail(String msg) {
        return new R<>(RestCode.FAILURE.code, msg, null);
    }

    /**
     * {code:rc.code, msg:rc.msg, data:null, success:false}
     */
    public static R<?> fail(IRestCode rc) {
        return new R<>(rc.getCode(), rc.getMsg(), null, false);
    }

    //################################################################################################################//

    /**
     * {code:400, msg:操作失败, data:data, success:false}
     */
    public static <T> R<T> fail(T data) {
        return fail(data, RestCode.FAILURE.msg);
    }

    /**
     * {code:400, msg:自定义msg, data:data, success:false}
     */
    public static <T> R<T> fail(T data, String msg) {
        return fail(data, msg, RestCode.FAILURE.code);
    }

    /**
     * {code:自定义code, msg:自定义msg, data:data, success:false}
     */
    public static <T> R<T> fail(T data, String msg, int code) {
        return new R<>(code, msg, data, false);
    }

    //endregion


    //region gt&st

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    //endregion

    //region toString

    @Override
    public String toString() {
        return "R{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                ", success=" + success +
                '}';
    }

    //endregion
}
