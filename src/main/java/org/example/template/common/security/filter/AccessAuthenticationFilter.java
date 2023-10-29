package org.example.template.common.security.filter;

import org.example.template.common.utils.JWTUtil;
import org.example.template.common.utils.Response;
import org.example.template.common.utils.ResponseCode;
import org.example.template.common.utils.ResponseUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 访问认证过滤器，用户每次访问都会走此过滤器，用于用户访问的认证
 */
public class AccessAuthenticationFilter extends BasicAuthenticationFilter {
    private RedisTemplate redisTemplate;

    public AccessAuthenticationFilter(AuthenticationManager authenticationManager, RedisTemplate redisTemplate) {
        super(authenticationManager);
        this.redisTemplate = redisTemplate;
    }

    // 每次请求都先执行此方法进行过滤
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        // 进行token认证
        String token = request.getHeader("token");
        if (JWTUtil.checkToken(token)) {
            // 打印访问信息
            String userName = JWTUtil.getUserNameByJwtToken(token);
            logger.info(userName + " " + request.getMethod() + " Request URI:" + request.getRequestURI());

            // token认证通过，获取认证信息
            String userId = JWTUtil.getUserIdByJwtToken(token);
            Authentication authentication = getAuthentication(userId);
            if (authentication != null) {
                // 设置当前用户的身份认证信息，后续的请求处理会用该身份认证信息进行授权和访问控制
                SecurityContextHolder.getContext().setAuthentication(authentication);
                // 放行
                chain.doFilter(request, response);
                return;
            }
        }

        // 认证失败，响应前端，也可抛出异常让AuthenticationFailedEntrance去处理
        ResponseUtil.out(response, Response.error().code(ResponseCode.TOKEN_ERROR)
                .message("Token认证已失效，请重新登录！"));
    }

    private Authentication getAuthentication(String userId) {
        // 从redis获取该用户分配的权限
        List<String > permissionValueList = (List<String>) redisTemplate.opsForValue().get(userId);
        if (permissionValueList == null) {
            return null;
        }

        // 更新redis过期时间
        redisTemplate.expire(userId, JWTUtil.EXPIRE, TimeUnit.MILLISECONDS);

        // 封装成GrantedAuthority集合对象的形式
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        for (String permission : permissionValueList) {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(permission);
            authorities.add(authority);
        }

        return new UsernamePasswordAuthenticationToken(userId, null, authorities);
    }
}
