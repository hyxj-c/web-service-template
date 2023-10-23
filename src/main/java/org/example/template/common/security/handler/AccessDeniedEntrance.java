package org.example.template.common.security.handler;

import org.example.template.common.utils.Response;
import org.example.template.common.utils.ResponseUtil;
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
        ResponseUtil.out(response, Response.error().message("您没有此操作的权限，请联系管理员添加"));
    }
}
