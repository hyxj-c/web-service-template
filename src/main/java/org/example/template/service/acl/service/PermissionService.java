package org.example.template.service.acl.service;

import org.example.template.common.utils.Response;
import org.example.template.service.acl.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 权限表 服务类
 * </p>
 *
 * @author lyj
 * @since 2023-08-15
 */
public interface PermissionService extends IService<Permission> {

    /**
     * 获取菜单结构的权限
     * @return 权限列表
     */
    List<Permission> getPermissionMenuStructure();

    /**
     * 根据角色id获取树结构权限
     * @param roleId 角色id
     * @return 权限列表
     */
    List<Permission> getPermissionTreeByRoleId(String roleId);

    /**
     * 根据用户id获取该用户分配的权限的树结构展示
     * @param userId 用户id
     * @return 权限列表
     */
    List<Permission> getAssignedPermissionTreeByUserId(String userId);

    /**
     * 根据用户id获取该用户分配的按钮权限值列表
     * @param userId 用户id
     * @return 权限值列表
     */
    List<String> getButtonPermissionValuesByUserId(String userId);

    /**
     * 根据权限id递归删除权限
     * @param id 权限id
     * @return
     */
    Response recursionRemovePermissionsById(String id);

    /**
     * 批量删除权限
     * @param idList 权限id列表
     * @return
     */
    Response batchRemovePermissions(List<String> idList);
}
