package org.example.template.service.acl.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.example.template.common.utils.Response;
import org.example.template.service.acl.entity.User;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户 服务类
 * </p>
 *
 * @author lyj
 * @since 2023-08-15
 */
public interface UserService extends IService<User> {

    /**
     * 分页获取用户列表
     * @param currentPage 当前页
     * @param pageSize 每页大小
     * @param user 要进行筛选查询的user对象
     * @return 响应对象
     */
    Response getUserList(long currentPage, long pageSize, User user);

    /**
     * 根据用户名获取用户
     * @param username 用户名
     * @return 用户对象
     */
    User getUserByUsername(String username);

    /**
     * 添加用户
     * @param user 用户对象
     * @return 响应对象
     */
    Response addUser(User user);

    /**
     * 修改用户
     * @param user 用户对象
     * @return 响应对象
     */
    Response updateUser(User user);

    /**
     * 修改用户密码
     * @param userId 用户id
     * @param originalPassword 原密码
     * @param newPassword 新密码
     * @return 响应对象
     */
    Response updateUserPassword(String userId, String originalPassword, String newPassword);

    /**
     * 根据用户id删除用户
     * @param id 用户id
     */
    void removeUserById(String id);

    /**
     * 批量删除用户
     * @param idList 用户id列表
     */
    void batchRemoveUsers(List<String> idList);

    /**
     * 设置用户角色
     * @param userId 用户id
     * @param roleIdList 角色id列表
     */
    void saveUserRoleRelation(String userId, List<String> roleIdList);

    /**
     * 用户登录
     * @param username 用户名
     * @param password 密码
     * @return 用户信息
     */
    User login(String username, String password);

    /**
     * 根据用户id获取该用户的信息
     * @param userId 用户id
     * @return 用户信息键值
     */
    Map<String, Object> getUserInfoByUserId(String userId);

    /**
     * 根据用户id获取该用户的权限路由
     * @param userId 用户id
     * @return 权限列表
     */
    List<JSONObject> getPermissionRouteByUserId(String userId);

}
