package org.example.template.service.acl.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.template.common.utils.MD5Util;
import org.example.template.common.utils.Response;
import org.example.template.service.acl.entity.Permission;
import org.example.template.service.acl.entity.Role;
import org.example.template.service.acl.entity.User;
import org.example.template.service.acl.entity.UserRole;
import org.example.template.service.acl.mapper.UserMapper;
import org.example.template.service.acl.service.PermissionService;
import org.example.template.service.acl.service.RoleService;
import org.example.template.service.acl.service.UserRoleService;
import org.example.template.service.acl.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
    @Autowired
    private PermissionService permissionService;

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
    public User getUserByUsername(String username) {
        User user = baseMapper.selectOne(new QueryWrapper<User>().eq("username", username));

        return user;
    }

    @Override
    public Response addUser(User user) {
        // 判断用户名是否存在
        if (userNameExist(user.getUsername())) {
            return Response.error().message("用户名已存在！");
        }

        // 对密码进行加密处理
        user.setPassword(MD5Util.encrypt(user.getPassword()));

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
    public Response updateUserPassword(String userId, String originalPassword, String newPassword) {
        // 判断用户的原密码是否正确
        User user = baseMapper.selectById(userId);
        if (!MD5Util.encrypt(originalPassword).equals(user.getPassword())) {
            return Response.error().message("原密码不正确！");
        }

        // 进行修改
        user = new User();
        user.setId(userId);
        user.setPassword(MD5Util.encrypt(newPassword));
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

    @Override
    public Map<String, Object> getUserInfoByUserId(String userId) {
        Map<String, Object> userInfo = new HashMap<>();
        // 获取该用户信息
        User user = baseMapper.selectById(userId);
        if (user != null) {
            userInfo.put("id", user.getId());
            userInfo.put("username", user.getUsername());
            userInfo.put("avatar", "");
            if (StringUtils.hasLength(user.getRoleName())) {
                userInfo.put("roles", user.getRoleName().split("、"));
            }
        }
        // 获取用户的按钮权限值
        List<String> buttonPermissionList = permissionService.getButtonPermissionValuesByUserId(userId);
        userInfo.put("buttonPermissionList", buttonPermissionList);

        return userInfo;
    }

    @Override
    public List<JSONObject> getRouteMenuByUserId(String userId) {
        // 根据用户id获取已分配的权限树结构
        List<Permission> permissionTree = permissionService.getAssignedPermissionTreeByUserId(userId);

        // 构建前端路由菜单
        List<JSONObject> routeMenu = buildRouteMenu(permissionTree);

        return routeMenu;
    }

    /**
     * 构建路由菜单
     * @param permissionTree 权限树
     * @return 路由菜单列表
     */
    private List<JSONObject> buildRouteMenu(List<Permission> permissionTree) {
        // 最终构建好的路由菜单
        List<JSONObject> routeMenu = new ArrayList<>();

        if (permissionTree.size() == 1) {
            // 获取顶级菜单（顶级菜单不作为路由）
            Permission topMenu = permissionTree.get(0);
            // 获取顶级菜单下的一级权限
            List<Permission> onePermissionList = topMenu.getChildren();
            // 进行路由构建
            recursivelyBuild(routeMenu, onePermissionList);
        }

        return routeMenu;
    }

    /**
     * 递归构建路由菜单
     * @param routeMenu 封装构建好的路由菜单的集合
     * @param permissionList 要构建的权限列表
     */
    private void recursivelyBuild(List<JSONObject> routeMenu, List<Permission> permissionList) {
        for (Permission permission : permissionList) {
            if (permission.getPath() == null || "".equals(permission.getPath())) {
                continue;
            }

            JSONObject route = new JSONObject();
            route.put("path", permission.getPath());
            route.put("component", permission.getComponent());
            route.put("name", permission.getName() + "_" + permission.getId());

            JSONObject meta = new JSONObject();
            meta.put("title", permission.getName());
            meta.put("icon", permission.getIcon());
            meta.put("hidden", permission.getType() == 2); // type为2是按钮，不进行菜单显示
            route.put("meta", meta);

            List<JSONObject> childrenRoute = new ArrayList<>();
            route.put("children", childrenRoute);

            routeMenu.add(route);

            List<Permission> permissionChildrenList = permission.getChildren();
            if (permissionChildrenList != null && permissionChildrenList.size() > 0) {
                // 递归构建
                recursivelyBuild(childrenRoute, permissionChildrenList);
            }
        }
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
