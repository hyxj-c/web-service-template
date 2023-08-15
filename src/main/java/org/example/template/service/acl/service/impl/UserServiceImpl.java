package org.example.template.service.acl.service.impl;

import org.example.template.service.acl.entity.User;
import org.example.template.service.acl.mapper.UserMapper;
import org.example.template.service.acl.service.UserService;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public void insertUser(User user) {
        baseMapper.insert(user);
    }
}
