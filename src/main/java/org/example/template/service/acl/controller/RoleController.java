package org.example.template.service.acl.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.example.template.common.utils.Response;
import org.example.template.service.acl.entity.Role;
import org.example.template.service.acl.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
            @ApiParam(value = "每页显示的条数", required = true) @PathVariable long size
    ) {
        Page<Role> page = new Page<>(current, size);
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.select("name");
        roleService.page(page, wrapper);

        return Response.success().data("total", page.getTotal()).data("items", page.getRecords());
    }

    @PostMapping
    @ApiOperation(value = "添加角色")
    public Response addRole(
            @ApiParam(value = "角色对象，只有name属性是必须的，其它属性自动生成", required = true)
            @RequestBody Role role
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
        roleService.removeById(id);

        return Response.success().message("删除成功！");
    }

}

