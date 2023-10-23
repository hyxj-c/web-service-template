package org.example.template.common.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.template.common.security.entity.AuthenticationUser;
import org.example.template.common.security.entity.SecurityUser;
import org.example.template.common.utils.JWTUtil;
import org.example.template.common.utils.Response;
import org.example.template.common.utils.ResponseUtil;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

/**
 * 用户登录过滤器，对用户名密码进行校验
 */
public class UserLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;
    private RedisTemplate redisTemplate;

    public UserLoginFilter(AuthenticationManager authenticationManager, RedisTemplate redisTemplate) {
        this.authenticationManager = authenticationManager;
        this.redisTemplate = redisTemplate;

        // 配置登录的路径
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/security/login", "POST"));
    }


    // 用户登录时执行此方法进行登录认证
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // 将请求中的json字符串转换为User对象
            SecurityUser user = new ObjectMapper().readValue(request.getInputStream(), SecurityUser.class);

            // 创建一个用于用户名密码认证的认证对象，传入一个空集合用于封装权限
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                    user.getUsername(), user.getPassword(), new ArrayList<>());

            // 进行登录认证，会调用UserDetailsService实现类的loadUserByUsername进行登录认证
            return authenticationManager.authenticate(authentication);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 登录成功时执行此方法
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        // 获取当前登录用户的信息
        AuthenticationUser user = (AuthenticationUser) authResult.getPrincipal();
        SecurityUser currentUser = user.getCurrentUser();

        // 生成token
        String token = JWTUtil.generateToken(currentUser.getId(), currentUser.getUsername());

        // 把用户的权限信息存入redis
        redisTemplate.opsForValue().set(currentUser.getId(), user.getPermissionValueList());

        // 响应前端
        ResponseUtil.out(response, Response.success().data("id", currentUser.getId()).data("token", token));
    }

    // 登录失败时执行此方法
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        ResponseUtil.out(response, Response.error().message("用户名或密码错误！"));
    }
}
