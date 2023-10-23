package org.example.template.common.security.handler;

import org.example.template.common.utils.MD5Util;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 自定义的密码校验类
 */
public class DefaultPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        return MD5Util.encrypt(rawPassword.toString());
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return encodedPassword.equals(MD5Util.encrypt(rawPassword.toString()));
    }
}
