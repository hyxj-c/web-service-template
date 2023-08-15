package org.example.template.service.acl.service.impl;

import org.example.template.service.acl.entity.Permission;
import org.example.template.service.acl.mapper.PermissionMapper;
import org.example.template.service.acl.service.PermissionService;
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
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

}
