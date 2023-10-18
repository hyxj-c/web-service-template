package org.example.template.service.acl.service.impl;

import org.example.template.common.security.entity.AuthenticationUser;
import org.example.template.common.security.entity.SecurityUser;
import org.example.template.service.acl.entity.User;
import org.example.template.service.acl.service.PermissionService;
import org.example.template.service.acl.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("UserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 根据用户名获取用户信息
     * @param username 用户名
     * @return UserDetails
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 查询用户
        User user = userService.getUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("用户名不存在！");
        }

        // 获取用户的操作权限
        List<String> authorities = permissionService.getButtonPermissionValuesByUserId(user.getId());

        // 返回UserDetails实现类
        AuthenticationUser authenticationUser = new AuthenticationUser();
        SecurityUser securityUser = new SecurityUser();
        BeanUtils.copyProperties(user, securityUser);
        authenticationUser.setCurrentUser(securityUser);
        authenticationUser.setPermissionValueList(authorities);

        return authenticationUser;
    }
}
