package org.example.template.service.acl.service;

import org.example.template.common.utils.Response;
import org.example.template.service.acl.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 角色表 服务类
 * </p>
 *
 * @author lyj
 * @since 2023-08-15
 */
public interface RoleService extends IService<Role> {

    /**
     * 分页获取角色列表
     * @param currentPage 当前页
     * @param pageSize 每页大小
     * @param role 要进行筛选查询的role对象
     * @return 响应对象
     */
    Response getRoleList(long currentPage, long pageSize, Role role);

    /**
     * 获取所有角色
     * @return 角色列表
     */
    List<Role> getAllRoles();

    /**
     * 添加角色
     * @param role 角色对象
     * @return 响应对象
     */
    Response addRole(Role role);

    /**
     * 修改角色
     * @param role 角色对象
     * @return 响应对象
     */
    Response updateRole(Role role);

    /**
     * 删除角色
     * @param id 角色id
     * @return
     */
    Response removeRoleById(String id);

    /**
     * 批量删除角色
     * @param idList 角色id列表
     * @return
     */
    Response batchRemoveRoles(List<String> idList);

    /**
     * 根据用户id获取该用户分配的角色id
     * @param userId 用户id
     * @return 角色id列表
     */
    List<String> getAssignedRoleIdsByUserId(String userId);

    /**
     * 给角色分配权限
     * @param roleId 角色id
     * @param permissionIdList 要分配的权限id列表
     */
    void saveRolePermissionRelation(String roleId, List<String> permissionIdList);
}
