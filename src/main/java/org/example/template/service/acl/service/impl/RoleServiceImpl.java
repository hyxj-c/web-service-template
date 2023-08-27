package org.example.template.service.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.example.template.common.utils.Response;
import org.example.template.service.acl.entity.Role;
import org.example.template.service.acl.mapper.RoleMapper;
import org.example.template.service.acl.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
        int count = baseMapper.updateById(role);

        return Response.success().message("修改成功!");
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
