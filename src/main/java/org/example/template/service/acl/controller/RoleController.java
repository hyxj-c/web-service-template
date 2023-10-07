package org.example.template.service.acl.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.example.template.common.utils.Response;
import org.example.template.service.acl.entity.Role;
import org.example.template.service.acl.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 角色表 前端控制器
 * </p>
 *
 * @author lyj
 * @since 2023-08-15
 */
@RestController
@RequestMapping("/acl/role")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping("{current}/{size}")
    @ApiOperation(value = "获取角色列表")
    public Response getRoles(
            @ApiParam(value = "要查询的页数", required = true) @PathVariable long current,
            @ApiParam(value = "每页显示的条数", required = true) @PathVariable long size,
            @ApiParam(value = "查询对象", required = false) Role role
    ) {
        Response response = roleService.getRoleList(current, size, role);

        return response;
    }

    @GetMapping
    @ApiOperation(value = "获取所有角色")
    public Response getAllRoles() {
        List<Role> allRoles = roleService.getAllRoles();

        return Response.success().data("item", allRoles);
    }

    @GetMapping("getAssignedRoleIds/{userId}")
    @ApiOperation(value = "根据用户id获取该用户分配的角色id")
    public Response getAssignedRoleIdsByUserId(@PathVariable String userId) {
        List<String> roleIdList = roleService.getAssignedRoleIdsByUserId(userId);

        return Response.success().data("item", roleIdList);
    }

    @GetMapping("{id}")
    @ApiOperation(value = "根据id查询角色信息")
    public Response getRoleById(
            @ApiParam(value = "角色id", required = true) @PathVariable String id
    ) {
        Role role = roleService.getById(id);

        return Response.success().data("item", role);
    }

    @PostMapping
    @ApiOperation(value = "添加角色")
    public Response addRole(
            @ApiParam(value = "角色对象，只有name属性是必须的，其它属性自动生成", required = true)
            @Validated @RequestBody Role role
    ) {
        Response response = roleService.addRole(role);

        return response;
    }

    @PutMapping
    @ApiOperation(value = "修改角色")
    public Response updateRole(@RequestBody Role role) {
        Response response = roleService.updateRole(role);

        return response;
    }

    @DeleteMapping("{id}")
    @ApiOperation(value = "删除角色")
    public Response removeRole(@PathVariable String id) {
        roleService.removeRoleById(id);

        return Response.success().message("删除成功！");
    }

    @DeleteMapping
    @ApiOperation(value = "根据id列表批量删除角色")
    public Response batchRemoveRoles(
            @ApiParam(value = "角色id数组", required = true) @RequestBody List<String> idList
    ) {
        roleService.batchRemoveRoles(idList);

        return Response.success().message("删除成功！");
    }

    @PostMapping("assignPermission")
    @ApiOperation(value = "给角色分配权限")
    public Response assignPermission(@RequestParam String roleId, @RequestBody List<String> permissionIdList) {
        roleService.saveRolePermissionRelation(roleId, permissionIdList);

        return Response.success().message("分配成功！");
    }

}

