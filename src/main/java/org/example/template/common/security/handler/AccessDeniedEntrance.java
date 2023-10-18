package org.example.template.common.security.handler;

import org.example.template.common.servicebase.exception.ServiceException;
import org.example.template.common.utils.ResponseCode;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 没有权限访问的统一处理入口
 */
public class AccessDeniedEntrance implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {
        System.err.println("无权访问");
        throw new ServiceException(ResponseCode.SERVICE_ERROR, "您没有此操作的权限，请联系管理员添加");
    }
}
