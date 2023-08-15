package org.example.template.service.acl.controller;


import org.example.template.common.utils.Response;
import org.example.template.service.acl.entity.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author lyj
 * @since 2023-08-14
 */
@RestController
@RequestMapping("/acl/permission")
public class PermissionController {

    @GetMapping("hello")
    public User hello(User user) {
        user.setUsername("test");
        return user;
    }

}

