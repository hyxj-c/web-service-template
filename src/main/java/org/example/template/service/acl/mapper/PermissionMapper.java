package org.example.template.service.acl.mapper;

import org.example.template.service.acl.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 权限表 Mapper 接口
 * </p>
 *
 * @author lyj
 * @since 2023-08-15
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 根据用户id查询权限
     * @param userId 用户id
     * @return 权限列表
     */
    List<Permission> selectPermissionByUserId(String userId);
}
