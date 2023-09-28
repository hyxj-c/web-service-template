package org.example.template.service.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.template.common.servicebase.exception.ServiceException;
import org.example.template.common.utils.Response;
import org.example.template.common.utils.ResponseCode;
import org.example.template.service.acl.entity.Role;
import org.example.template.service.acl.entity.UserRole;
import org.example.template.service.acl.mapper.RoleMapper;
import org.example.template.service.acl.service.RoleService;
import org.example.template.service.acl.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色表 服务实现类
 * </p>
 *
 * @author lyj
 * @since 2023-08-15
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
    private UserRoleService userRoleService;

    @Override
    public Response getRoleList(long currentPage, long pageSize, Role role) {
        Page<Role> page = new Page<>(currentPage, pageSize);
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.select("id", "name");
        if (role.getName() != null) {
            wrapper.like("name", role.getName());
        }
        this.page(page, wrapper);

        return Response.success().data("total", page.getTotal()).data("items", page.getRecords());
    }

    @Override
    public List<Role> getAllRoles() {
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.select("id", "name");
        List<Role> roles = baseMapper.selectList(wrapper);

        return roles;
    }

    @Override
    public Response addRole(Role role) {
        // 判断角色名是否重复
        if (roleNameExist(role.getName())) {
            return Response.error().message("此角色已存在!");
        }

        // 添加角色
        baseMapper.insert(role);

        return Response.success().message("添加成功!");
    }

    @Override
    public Response updateRole(Role role) {
        // 判断角色名是否重复
        if (roleNameExist(role.getName())) {
            return Response.error().message("此角色已存在!");
        }

        // 修改角色
        baseMapper.updateById(role);

        return Response.success().message("修改成功!");
    }

    @Override
    public void removeRoleById(String id) {
        // 查询是否还有用户分配了此角色
        int count = userRoleService.count(new QueryWrapper<UserRole>().eq("role_id", id));
        if (count > 0) {
            throw new ServiceException(ResponseCode.SERVICE_ERROR, "有用户分配了此角色，请先解除用户对该角色的分配，在删除");
        }

        // 进行删除
        baseMapper.deleteById(id);
    }

    @Override
    public void batchRemoveRoles(List<String> idList) {
        // 查询是否还有用户分配了要删除的角色
        int count = userRoleService.count(new QueryWrapper<UserRole>().in("role_id", idList));
        if (count > 0) {
            throw new ServiceException(ResponseCode.SERVICE_ERROR, "有用户分配了要删除的角色，请先解除用户对要删除角色的分配，在删除");
        }

        // 进行删除
        baseMapper.deleteBatchIds(idList);
    }

    @Override
    public List<String> getAssignedRoleIdsByUserId(String userId) {
        // 根据用户id获取该用户的所有角色id
        List<UserRole> userRoleList = userRoleService
                .list(new QueryWrapper<UserRole>().select("role_id").eq("user_id", userId));
        List<String> roleIdList = userRoleList.stream().map(userRole -> userRole.getRoleId())
                .collect(Collectors.toList());

        return roleIdList;
    }

    /**
     * 判断此角色是否存在
     * @param name 角色名
     * @return 存在返回true，不存在返回false
     */
    private boolean roleNameExist(String name) {
        // 构建mybatis-plus查询条件构造器
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        wrapper.select("name").eq("name", name);
        // 进行查询
        Role role = baseMapper.selectOne(wrapper);

        if (role == null) {
            return false;
        } else {
            return true;
        }
    }

}
