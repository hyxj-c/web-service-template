package org.example.template.common.security.config;

import org.example.template.common.security.filter.AccessAuthenticationFilter;
import org.example.template.common.security.filter.UserLoginFilter;
import org.example.template.common.security.handler.AccessDeniedEntrance;
import org.example.template.common.security.handler.AuthenticationFailedEntrance;
import org.example.template.common.security.handler.DefaultPasswordEncoder;
import org.example.template.common.security.handler.UserLogoutHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Resource
    private UserDetailsService userDetailsService;
    @Resource
    private RedisTemplate redisTemplate;

    // 基础配置设置
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 禁用csrf跨站请求伪造
        http.csrf().disable();

        // 配置请求处理
        http.authorizeRequests().anyRequest().authenticated();

        // 添加过滤器
        http.addFilter(new UserLoginFilter(authenticationManager(), redisTemplate)) // 登录过滤器
                .addFilter(new AccessAuthenticationFilter(authenticationManager(), redisTemplate)); // 访问认证过滤器

        // 配置处理器
        http.exceptionHandling()
                .authenticationEntryPoint(new AuthenticationFailedEntrance()) // 认证失败的处理器
                .accessDeniedHandler(new AccessDeniedEntrance());  // 无权限访问的处理器

        // 配置退出
        http.logout().logoutUrl("/security/logout").addLogoutHandler(new UserLogoutHandler(redisTemplate));

        // 开启跨越
        http.cors();
    }

    // 用户认证设置
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        // 配置获取用户信息和进行密码校验的类
        auth.userDetailsService(userDetailsService).passwordEncoder(new DefaultPasswordEncoder());
    }

    // web安全配置
    @Override
    public void configure(WebSecurity web) throws Exception {
        // 配置不进入认证过滤器认证就可以访问的路径
        web.ignoring().antMatchers("/swagger-ui.html/**", "/swagger-resources/**",
                "/webjars/**", "/v2/**"); // swagger资源路径
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

        return new CorsFilter(source);
    }
}
