package org.example.template.common.security.handler;

import org.example.template.common.utils.JWTUtil;
import org.example.template.common.utils.Response;
import org.example.template.common.utils.ResponseCode;
import org.example.template.common.utils.ResponseUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户登出处理器
 */
public class UserLogoutHandler implements LogoutHandler {
    private RedisTemplate redisTemplate;

    public UserLogoutHandler(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String token = request.getHeader("token");
        if (JWTUtil.checkToken(token)) {
            String userId = JWTUtil.getUserIdByJwtToken(token);
            // 清除redis中的用户权限信息
            redisTemplate.delete(userId);
            ResponseUtil.out(response, Response.success().message("退出成功！"));
        } else {
            ResponseUtil.out(response, Response.error().code(ResponseCode.TOKEN_ERROR)
                    .message("Token认证已失效，请重新登录！"));
        }
    }
}
