package org.example.template.service.acl.controller;


import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.example.template.common.utils.Response;
import org.example.template.service.acl.entity.User;
import org.example.template.service.acl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @PostMapping("login")
    @ApiOperation(value = "用户登录")
    public Response userLogin(@RequestBody User user) {
        // 验证用户名和密码
        String username = user.getUsername();
        String password = user.getPassword();
        if (username != null && "".equals(username)) {
            return Response.error().message("用户名不能为空！");
        }
        if (password != null && "".equals(password)) {
            return Response.error().message("密码不能为空！");
        }

        User userInfo = userService.login(username, password);
        if (userInfo == null) {
            return Response.error().message("用户名或密码错误!");
        }

        return Response.success().data("item", userInfo).data("token", "mytoken");
    }

    @PutMapping("updatePassword")
    @ApiOperation(value = "修改用户密码")
    public Response updateUserPassword(
            @ApiParam(value = "前端对象需要包含三个参数userId，originalPassword，newPassword")
            @RequestBody Map<String, String> map)
    {
        String userId = map.get("userId");
        String originalPassword = map.get("originalPassword");
        String newPassword = map.get("newPassword");

        Response response = userService.updateUserPassword(userId, originalPassword, newPassword);

        return response;
    }

    @GetMapping("getPermissionRoute/{userId}")
    @ApiOperation(value = "根据用户id获取该用户的权限路由")
    public Response getPermissionRoute(@PathVariable String userId) {
        List<JSONObject> permissionRoute = userService.getPermissionRouteByUserId(userId);

        return Response.success().data("item", permissionRoute);
    }

    @GetMapping("getUserInfo/{userId}")
    @ApiOperation(value = "根据用户id获取用户信息")
    public Response getUserInfo(@PathVariable String userId) {
        Map<String, Object> userInfo = userService.getUserInfoByUserId(userId);

        return Response.success().data(userInfo);
    }

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
    public Response getUserById(@PathVariable String id) {
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
        userService.removeUserById(id);

        return Response.success().message("删除成功！");
    }

    @DeleteMapping
    @ApiOperation(value = "根据id列表批量删除用户")
    public Response batchRemoveUsers(
            @ApiParam(value = "用户id数组", required = true) @RequestBody List<String> idList
    ) {
        userService.batchRemoveUsers(idList);

        return Response.success().message("删除成功");
    }

    @PostMapping("assignRole")
    @ApiOperation(value = "给用户分配角色")
    public Response assignRole(@RequestParam String userId, @RequestBody List<String> roleIdList) {
        userService.saveUserRoleRelation(userId, roleIdList);

        return Response.success().message("分配成功");
    }

}

