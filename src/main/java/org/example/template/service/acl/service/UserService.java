package org.example.template.service.acl.service;

import org.example.template.service.acl.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author lyj
 * @since 2023-08-14
 */
public interface UserService extends IService<User> {

    void insertUser(User user);

}
