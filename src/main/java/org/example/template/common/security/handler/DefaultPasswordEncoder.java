package org.example.template.common.security.handler;

import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 自定义的密码校验类
 */
public class DefaultPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        System.err.println("encode()");
        return null;
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        System.err.println("matches()");
        return true;
    }
}
