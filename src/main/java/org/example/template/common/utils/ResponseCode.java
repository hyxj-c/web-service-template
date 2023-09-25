package org.example.template.common.utils;

/**
 * 响应结果返回的状态码
 */
public interface ResponseCode {
    Integer SUCCESS = 2000;
    Integer ERROR = 4000;
    Integer TOKEN_ERROR = 4001;
    Integer SERVICE_ERROR = 4002;
    Integer ARGUMENT_VALIDATE_ERROR = 4003;
}
