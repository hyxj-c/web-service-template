package org.example.template.service.acl.service;

import org.example.template.common.utils.Response;
import org.example.template.service.acl.entity.Role;
import com.baomidou.mybatisplus.extension.service.IService;

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
}
