package org.example.template.service.acl.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.example.template.common.utils.Response;
import org.example.template.service.acl.entity.User;
import org.example.template.service.acl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户 前端控制器
 * </p>
 *
 * @author lyj
 * @since 2023-08-15
 */
@RestController
@RequestMapping("/acl/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("{current}/{size}")
    @ApiOperation(value = "获取用户列表")
    public Response getUsers(
            @ApiParam(value = "要查询的页数", required = true) @PathVariable long current,
            @ApiParam(value = "每页显示的条数", required = true) @PathVariable long size,
            @ApiParam(value = "查询对象", required = false) User user
    ) {
        Response response = userService.getUserList(current, size, user);

        return response;
    }

    @GetMapping("{id}")
    @ApiOperation(value = "根据id查询用户信息")
    public Response getRoleById(
            @ApiParam(value = "用户id", required = true) @PathVariable String id
    ) {
        User user = userService.getById(id);

        return Response.success().data("item", user);
    }

    @PostMapping
    @ApiOperation(value = "添加用户")
    public Response addUser(@Validated @RequestBody User user) {
        Response response = userService.addUser(user);
        
        return response;
    }

    @PutMapping
    @ApiOperation(value = "修改用户")
    public Response updateUser(@RequestBody User user) {
        Response response = userService.updateUser(user);

        return response;
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "删除用户")
    public Response removeUser(@PathVariable String id) {
        userService.removeById(id);

        return Response.success().message("删除成功！");
    }

    @DeleteMapping
    @ApiOperation(value = "根据id列表批量删除用户")
    public Response batchRemoveRoles(@RequestBody List<String> idList) {
        userService.removeByIds(idList);

        return Response.success().message("删除成功");
    }

}

