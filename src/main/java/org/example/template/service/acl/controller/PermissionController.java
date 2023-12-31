package org.example.template.service.acl.controller;


import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.example.template.common.utils.Response;
import org.example.template.service.acl.entity.Permission;
import org.example.template.service.acl.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 权限表 前端控制器
 * </p>
 *
 * @author lyj
 * @since 2023-08-15
 */
@RestController
@RequestMapping("/acl/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;

    @GetMapping
    @PreAuthorize("hasAuthority('permission.list')")
    @ApiOperation(value = "获取菜单结构权限")
    public Response getPermissionMenu(){
        List<Permission> permissionMenuStructure = permissionService.getPermissionMenuStructure();

        return Response.success().data("item", permissionMenuStructure);
    }

    @GetMapping("{roleId}")
    @PreAuthorize("hasAuthority('role.viewPermission')")
    @ApiOperation(value = "根据角色id获取树结构权限")
    public Response getPermissionTree(@PathVariable String roleId) {
        List<Permission> permissionTreeList = permissionService.getPermissionTreeByRoleId(roleId);

        return Response.success().data("item", permissionTreeList);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('permission.add')")
    @ApiOperation(value = "添加权限")
    public Response addPermission(@Validated @RequestBody Permission permission) {
        permissionService.save(permission);

        return Response.success().message("添加成功！");
    }

    @PutMapping
    @PreAuthorize("hasAuthority('permission.update')")
    @ApiOperation(value = "修改权限")
    public Response updatePermission(@RequestBody Permission permission) {
        permissionService.updateById(permission);

        return Response.success().message("修改成功！");
    }

    @DeleteMapping("recursionRemove/{id}")
    @PreAuthorize("hasAuthority('permission.remove')")
    @ApiOperation(value = "递归删除权限")
    public Response recursionRemovePermissions(@PathVariable String id) {
        Response response = permissionService.recursionRemovePermissionsById(id);

        return response;
    }

    @DeleteMapping
    @PreAuthorize("hasAuthority('permission.remove')")
    @ApiOperation(value = "根据id列表批量删除权限")
    public Response batchRemovePermissions(
            @ApiParam(value = "权限id数组", required = true) @RequestBody List<String> idList
    ) {
        Response response = permissionService.batchRemovePermissions(idList);

        return response;
    }

}

