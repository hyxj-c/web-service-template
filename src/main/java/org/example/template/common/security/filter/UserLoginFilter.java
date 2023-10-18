package org.example.template.common.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.template.common.security.entity.SecurityUser;
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

    public UserLoginFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;

        // 配置登录的路径
        this.setRequiresAuthenticationRequestMatcher(new AntPathRequestMatcher("/acl/user/login", "POST"));
    }


    // 用户登录时执行此方法进行认证
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            // 将请求中的json字符串转换为User对象
            SecurityUser user = new ObjectMapper().readValue(request.getInputStream(), SecurityUser.class);
            // 传入用户名和密码进行登录认证，传入一个空集合用于装权限，之后会调用UserDetailsService实现类的loadUserByUsername方法获取用户信息
            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    user.getUsername(), user.getPassword(), new ArrayList<>()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // 登录成功时执行此方法
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        System.err.println("登录成功");
    }

    // 登录失败时执行此方法
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        System.err.println("登录失败");
    }
}
