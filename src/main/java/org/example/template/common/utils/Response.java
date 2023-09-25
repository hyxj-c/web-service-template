package org.example.template.common.utils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 统一响应结果类
 */
@Data
public class Response {
    private Boolean success; // 是否成功
    private Integer code; // 状态码
    private String message; // 响应消息
    private Map<String, Object> data = new HashMap<>(); // 响应数据

    private Response(){}

    /**
     * 成功的返回
     * @return 响应对象
     */
    public static Response success() {
        Response response = new Response();
        response.success = true;
        response.code = ResponseCode.SUCCESS;
        response.message = "成功";

        return response;
    }

    /**
     * 失败的返回
     * @return 响应对象
     */
    public static Response error() {
        Response response = new Response();
        response.success = false;
        response.code = ResponseCode.ERROR;
        response.message = "失败";

        return response;
    }

    public Response success(Boolean bool) {
        this.success = bool;

        return this;
    }

    public Response code(Integer code) {
        this.code = code;

        return this;
    }

    public Response message(String message) {
        this.message = message;

        return this;
    }

    public Response data(String key, Object value) {
        this.data.put(key, value);

        return this;
    }

    public Response data(Map<String, Object> data) {
        this.data = data;

        return this;
    }

}


