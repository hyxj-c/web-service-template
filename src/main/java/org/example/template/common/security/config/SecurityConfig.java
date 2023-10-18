package org.example.template.common.security.config;

import org.example.template.common.security.filter.UserLoginFilter;
import org.example.template.common.security.handler.AccessDeniedEntrance;
import org.example.template.common.security.handler.DefaultPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import javax.annotation.Resource;

/**
 * spring security配置类
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private UserDetailsService userDetailsService;

    // 基础配置设置
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 禁用csrf跨站请求伪造
        http.csrf().disable();

        // 配置请求处理
        http.authorizeRequests()
                .antMatchers("/swagger-ui.html/**", "/swagger-resources/**", "/webjars/**",
                        "/v2/**", "/acl/user/login").permitAll() // 配置未认证可访问的路径
                .anyRequest().authenticated();

        // 添加过滤器
        http.addFilter(new UserLoginFilter(authenticationManager())); // 登录过滤器

        // 配置处理器
        http.exceptionHandling()
                .accessDeniedHandler(new AccessDeniedEntrance()); // 无权限访问的处理器

    }

    // 用户认证设置
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 配置获取用户信息和进行密码校验的类
        auth.userDetailsService(userDetailsService).passwordEncoder(new DefaultPasswordEncoder());
    }

    // 过滤器配置跨域
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return  new CorsFilter(source);
    }
}
