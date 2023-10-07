package org.example.template.service.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.template.service.acl.entity.RolePermission;
import org.example.template.service.acl.mapper.RolePermissionMapper;
import org.example.template.service.acl.service.RolePermissionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 角色权限 服务实现类
 * </p>
 *
 * @author lyj
 * @since 2023-08-15
 */
@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

    @Override
    public List<String> getPermissionIdListByRoleId(String roleId) {
        // 根据角色id获取该角色分配的所有权限
        List<RolePermission> rolePermissionList = baseMapper
                .selectList(new QueryWrapper<RolePermission>().select("permission_id").eq("role_id", roleId));

        // 把角色权限集合转换为权限id集合
        List<String> permissionIdList = rolePermissionList.stream()
                .map(rolePermission -> rolePermission.getPermissionId()).collect(Collectors.toList());

        return permissionIdList;
    }
}
