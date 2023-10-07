package org.example.template.service.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.template.common.utils.Response;
import org.example.template.service.acl.entity.Role;
import org.example.template.service.acl.entity.User;
import org.example.template.service.acl.entity.UserRole;
import org.example.template.service.acl.mapper.UserMapper;
import org.example.template.service.acl.service.RoleService;
import org.example.template.service.acl.service.UserRoleService;
import org.example.template.service.acl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 用户 服务实现类
 * </p>
 *
 * @author lyj
 * @since 2023-08-15
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;

    @Override
    public Response getUserList(long currentPage, long pageSize, User user) {
        Page<User> page = new Page<>(currentPage, pageSize);
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("id", "username", "role_name", "gmt_created");
        if (user.getUsername() != null) {
            wrapper.like("username", user.getUsername());
        }
        this.page(page, wrapper);

        return Response.success().data("total", page.getTotal()).data("items", page.getRecords());
    }

    @Override
    public Response addUser(User user) {
        // 判断用户名是否存在
        if (userNameExist(user.getUsername())) {
            return Response.error().message("用户名已存在！");
        }

        // 添加用户到数据库
        baseMapper.insert(user);

        return Response.success().message("添加成功！");
    }

    @Override
    public Response updateUser(User user) {
        // 判断用户名是否存在
        if (userNameExist(user.getUsername())) {
            return Response.error().message("用户名已存在！");
        }

        // 修改用户
        baseMapper.updateById(user);

        return Response.success().message("修改成功！");
    }

    @Override
    @Transactional
    public void removeUserById(String id) {
        // 删除该用户的角色(用户角色关系)
        userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id", id));

        // 删除该用户
        baseMapper.deleteById(id);
    }

    @Override
    @Transactional
    public void batchRemoveUsers(List<String> idList) {
        if (!idList.isEmpty()) {
            // 批量删除用户的角色
            userRoleService.remove(new QueryWrapper<UserRole>().in("user_id", idList));

            // 批量删除用户
            baseMapper.deleteBatchIds(idList);
        }
    }

    @Override
    @Transactional
    public void saveUserRoleRelation(String userId, List<String> roleIdList) {
        // 1.删除当前用户的角色
        userRoleService.remove(new QueryWrapper<UserRole>().eq("user_id", userId));

        // 2.设置当前用户的角色
        ArrayList<UserRole> userRolesList = new ArrayList<>();
        for (String roleId : roleIdList) {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRolesList.add(userRole);
        }
        userRoleService.saveBatch(userRolesList);

        // 3.查询出要设置的角色名
        String roleNames = "";
        if (!roleIdList.isEmpty()) {
            List<Role> roleList = roleService.list(new QueryWrapper<Role>().select("name").in("id", roleIdList));
            List<String> roleNameList = roleList.stream().map(role -> role.getName()).collect(Collectors.toList());
            roleNames = String.join("、", roleNameList );
        }

        // 4.设置当前用户的角色名
        User user = new User();
        user.setId(userId);
        user.setRoleName(roleNames);
        baseMapper.updateById(user);
    }

    /**
     * 判断用户名是否存在
     * @param username 用户名
     * @return 存在返回true，不存在返回false
     */
    private boolean userNameExist(String username) {
        // 构建mybatis-plus查询条件构造器
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.select("username").eq("username", username);
        // 进行查询
        User user = baseMapper.selectOne(wrapper);

        if (user == null) {
            return false;
        } else {
            return true;
        }
    }
}
