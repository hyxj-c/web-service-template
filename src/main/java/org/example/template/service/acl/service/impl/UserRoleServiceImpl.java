package org.example.template.service.acl.service.impl;

import org.example.template.service.acl.entity.UserRole;
import org.example.template.service.acl.mapper.RoleMapper;
import org.example.template.service.acl.mapper.UserRoleMapper;
import org.example.template.service.acl.service.UserRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户角色 服务实现类
 * </p>
 *
 * @author lyj
 * @since 2023-08-15
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

}
