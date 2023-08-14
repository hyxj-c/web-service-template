package org.example.template.common.servicebase.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.template.common.utils.Response;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理类
 */
@Slf4j // 输出日志到文件的注解
@ControllerAdvice
public class GlobalExceptionHandler {

    // 全局异常
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Response error(Exception e) {
        e.printStackTrace();
        // 输出错误日志，以便写入日志文件
        log.error(e.getClass().getName() + "--" + e.getMessage());

        return Response.error().message("出错了，请稍后再试...");
    }

    // 特定异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Response error(ArithmeticException e) {
        e.printStackTrace();
        log.error("ArithmeticException--" + e.getMessage());

        return Response.error().message("执行了特定异常");
    }

    // 自定义异常
    @ExceptionHandler(MyException.class)
    @ResponseBody
    public Response error(MyException e) {
        e.printStackTrace();
        log.error("MyException--" + e.getMsg());

        return Response.error().code(e.getCode()).message(e.getMsg());
    }

}
