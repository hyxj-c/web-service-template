package org.example.template.common.servicebase.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.template.common.utils.Response;
import org.example.template.common.utils.ResponseCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
    public Response exceptionHandler(Exception e) {
        e.printStackTrace();
        // 输出错误日志，以便写入日志文件
        log.error(e.getClass().getName() + "--" + e.getMessage() + "\n");

        return Response.error().message("出错了，请稍后再试...");
    }

    // 参数验证失败异常
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    public Response methodArgumentNotValidExceptionHandler(MethodArgumentNotValidException e) {
        e.printStackTrace();
        log.error("MethodArgumentNotValidException--" + e.getMessage() + "\n");

        // 从异常对象中拿到ObjectError对象
        ObjectError objectError = e.getBindingResult().getAllErrors().get(0);

        return Response.error().code(ResponseCode.ARGUMENT_VALIDATE_ERROR).message(objectError.getDefaultMessage());
    }

    // 使用spring security后，无权限访问的异常
    @ExceptionHandler(AccessDeniedException.class)
    public void error(AccessDeniedException e) {
        log.error("AccessDeniedException--" + e.getMessage() + "\n");
        // 这里抛出异常，让无权访问处理器统一处理
        throw e;
    }

    // 特定异常
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Response arithmeticExceptionHandler(ArithmeticException e) {
        e.printStackTrace();
        log.error("ArithmeticException--" + e.getMessage() + "\n");

        return Response.error().message("执行了特定异常");
    }

    // 自定义业务异常
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Response serviceExceptionHandler(ServiceException e) {
        e.printStackTrace();
        log.error("ServiceException--" + e.getMsg() + "\n");

        return Response.error().code(e.getCode()).message(e.getMsg());
    }

}
