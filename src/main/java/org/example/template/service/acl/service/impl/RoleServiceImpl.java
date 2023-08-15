package org.example.template.service.acl.service.impl;

import org.example.template.service.acl.entity.Role;
import org.example.template.service.acl.mapper.RoleMapper;
import org.example.template.service.acl.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lyj
 * @since 2023-08-14
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
