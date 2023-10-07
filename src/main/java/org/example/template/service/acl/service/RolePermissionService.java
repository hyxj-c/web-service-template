package org.example.template.service.acl.service;

import org.example.template.service.acl.entity.RolePermission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色权限 服务类
 * </p>
 *
 * @author lyj
 * @since 2023-08-15
 */
public interface RolePermissionService extends IService<RolePermission> {

    /**
     * 根据角色id获取该角色分配的所有权限id
     * @param roleId 角色id
     * @return 权限id列表
     */
    List<String> getPermissionIdListByRoleId(String roleId);

}
