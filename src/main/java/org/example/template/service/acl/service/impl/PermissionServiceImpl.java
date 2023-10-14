package org.example.template.service.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.template.common.utils.Response;
import org.example.template.service.acl.entity.Permission;
import org.example.template.service.acl.entity.RolePermission;
import org.example.template.service.acl.mapper.PermissionMapper;
import org.example.template.service.acl.service.PermissionService;
import org.example.template.service.acl.service.RolePermissionService;
import org.example.template.service.acl.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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
    @Autowired
    private RoleService roleService;

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
                .selectList(new QueryWrapper<Permission>().select("id", "pid", "name", "weight"));

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
    public List<Permission> getAssignedPermissionTreeByUserId(String userId) {
        // 查询该用户分配的所有权限
        List<Permission> permissionList = baseMapper.selectPermissionByUserId(userId);

        // 封装成树结构展示
        List<Permission> assignedPermissionTree = buildTreeStructure(permissionList);

        return assignedPermissionTree;
    }

    @Override
    public List<String> getButtonPermissionValuesByUserId(String userId) {
        // 获取该用户的所有角色
        List<String> roleIdList = roleService.getAssignedRoleIdsByUserId(userId);

        // 根据角色获取角色分配的所有权限id
        List<String> permissionIdList = rolePermissionService.getPermissionIdListByRoleIdList(roleIdList);

        // 根据权限id列表获取所有权限
        if (permissionIdList.isEmpty()) {
            return new ArrayList<>();
        }
        List<Permission> permissionList = baseMapper
                .selectList(new QueryWrapper<Permission>().select("permission_value")
                        .eq("type", 2).in("id", permissionIdList));

        // 把权限集合转化为权限值集合
        List<String> buttonValueList = permissionList.stream()
                .map(permission -> permission.getPermissionValue()).collect(Collectors.toList());

        return buttonValueList;
    }

    @Override
    public Response recursionRemovePermissionsById(String id) {
        // 创建list集合，用于封装所有要删除的权限id值
        List<String> idList = new ArrayList<>();
        // 存入当前的权限id
        idList.add(id);
        // 递归查询子权限id值，并封装到idList
        queryPermissionChildById(id, idList);

        // 判断是否有角色分配了要删除的权限
        if (judgeRolePermission(idList)) {
            return Response.error().message("有角色分配了要删除的权限，请先解除之后，在进行删除！");
        }

        // 进行删除
        baseMapper.deleteBatchIds(idList);

        return Response.success().message("删除成功！");
    }

    @Override
    public Response batchRemovePermissions(List<String> idList) {
        // 判断是否有角色分配了要删除的权限
        if (judgeRolePermission(idList)) {
            return Response.error().message("有角色分配了要删除的权限，请先解除之后，在进行删除！");
        }

        // 删除权限
        baseMapper.deleteBatchIds(idList);

        return Response.success().message("删除成功！");
    }

    /**
     * 判断是否有角色分配了要判断的权限
     * @param permissionIdList 权限id列表
     * @return 有角色分配返回true，无角色分配返回false
     */
    private boolean judgeRolePermission(List<String> permissionIdList) {
        if (permissionIdList.isEmpty()) {
            return false;
        }
        // 查询是否有角色分配了此权限
        int count = rolePermissionService.count(new QueryWrapper<RolePermission>().in("permission_id", permissionIdList));
        return count > 0;
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
        // 根据权限权重进行排序，方便前端按照排序展示
        permissionList.sort((o1, o2) -> o2.getWeight() - o1.getWeight());

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
