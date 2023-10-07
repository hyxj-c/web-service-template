package org.example.template.service.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.template.service.acl.entity.Permission;
import org.example.template.service.acl.entity.RolePermission;
import org.example.template.service.acl.mapper.PermissionMapper;
import org.example.template.service.acl.service.PermissionService;
import org.example.template.service.acl.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 权限表 服务实现类
 * </p>
 *
 * @author lyj
 * @since 2023-08-15
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {
    @Autowired
    private RolePermissionService rolePermissionService;

    @Override
    public List<Permission> getPermissionMenuStructure() {
        // 获取所有权限
        List<Permission> allPermissions = baseMapper.selectList(null);

        // 把所有权限封装成树结构的展示形式
        List<Permission> permissionTree = buildTreeStructure(allPermissions);

        return permissionTree;
    }

    @Override
    public List<Permission> getPermissionTreeByRoleId(String roleId) {
         // 获取所有权限
        List<Permission> permissionList = baseMapper
                .selectList(new QueryWrapper<Permission>().select("id", "pid", "name"));

        // 根据角色id获取该角色分配的所有权限的id
        List<String> permissionIdList = rolePermissionService.getPermissionIdListByRoleId(roleId);

        // 把该角色拥有的权限设置为选中状态
        for (Permission permission : permissionList) {
            if (permissionIdList.contains(permission.getId())) {
                permission.setSelected(true);
            }
        }

        // 封装成权限树结构
        List<Permission> permissionTree = buildTreeStructure(permissionList);

        return permissionTree;
    }

    @Override
    public void recursionRemovePermissionsById(String id) {
        // 创建list集合，用于封装所有要删除的权限id值
        List<String> idList = new ArrayList<>();
        // 存入当前的权限id
        idList.add(id);
        // 递归查询子权限id值，并封装到idList
        queryPermissionChildById(id, idList);

        baseMapper.deleteBatchIds(idList);

    }

    /**
     * 根据当前权限id，递归查询子权限id，封装到list集合
     * @param id 权限id
     * @param idList 存权限id的集合
     */
    private void queryPermissionChildById(String id, List<String> idList) {
        // 根据pid查询所有子权限
        List<Permission> childPermissionList = baseMapper
                .selectList(new QueryWrapper<Permission>().select("id").eq("pid", id));

        // 获取子权限id值，封装到idList里面，并做递归查询
        for (Permission permission : childPermissionList) {
            idList.add(permission.getId());
            queryPermissionChildById(permission.getId(), idList);
        }
    }

    /**
     * 把权限封装成树结构展示形式
     * @param permissionList 权限集合
     * @return 树结构的权限集合
     */
    private List<Permission> buildTreeStructure(List<Permission> permissionList) {
        // 最终构建好的树结构权限list
        List<Permission> permissionTreeList = new ArrayList<>();

        // 遍历权限list，进行构建
        for (Permission permission : permissionList) {
            // 找到顶层菜单(pid=0)，设置level为1
            if ("0".equals(permission.getPid())) {
                permission.setLevel(1);
                // 根据顶层菜单，向里面查询子菜单，封装到permissionTreeList里面
                permissionTreeList.add(queryBuildChildren(permission, permissionList));
            }
        }

        return permissionTreeList;
    }

    /**
     * 根据父级权限节点递归查询封装子级权限节点
     * @param parentPermission 父级权限节点
     * @param permissionList 权限列表
     * @return 封装好的父级权限节点
     */
    private Permission queryBuildChildren(Permission parentPermission, List<Permission> permissionList) {
        // 初始化Permission的children属性
        parentPermission.setChildren(new ArrayList<Permission>());

        // 遍历权限list，进行比较判断，比较id和pid是否相同
        for (Permission permission : permissionList) {
            if (parentPermission.getId().equals(permission.getPid())) {
                // 设置子权限的level值为父权限的level值加 1
                permission.setLevel(parentPermission.getLevel() + 1);
                // 把查询出来的子菜单放到父菜单里面，并进行递归查询
                parentPermission.getChildren().add(queryBuildChildren(permission, permissionList));
            }
        }

        return parentPermission;
    }
}
