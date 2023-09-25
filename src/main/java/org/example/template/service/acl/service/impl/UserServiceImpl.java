package org.example.template.service.acl.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.api.R;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.example.template.common.utils.Response;
import org.example.template.service.acl.entity.Role;
import org.example.template.service.acl.entity.User;
import org.example.template.service.acl.mapper.UserMapper;
import org.example.template.service.acl.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

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
