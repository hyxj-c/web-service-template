package org.example.template.service.acl.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.template.common.utils.Response;
import org.example.template.service.acl.entity.User;

import java.util.List;

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

}
