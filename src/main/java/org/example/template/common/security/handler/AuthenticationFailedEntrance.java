package org.example.template.common.security.handler;

import org.example.template.common.utils.Response;
import org.example.template.common.utils.ResponseUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 认证失败的统一处理入口
 */
public class AuthenticationFailedEntrance implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException, ServletException {
        System.err.println("认证失败：commence()");
        ResponseUtil.out(response, Response.error().message("认证失败！"));
    }
}
