package com.bili.util;

public class Response {
    // Response 实体提供了两个静态方法 success 和 failure 分别提供给调用成功和调用失败的时候使用
    // 接口返回对象中包含 code、msg 和 data 三个属性。
    private Integer code;
    private String msg;
    private Object data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Response(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public static Response success(Object data) {
        Response item = new Response(200, "success", data);
        return item;
    }
    public static Response failure(int errorCode, String errorMessage) {
        Response item = new Response(errorCode, errorMessage, null);
        return item;
    }
}
