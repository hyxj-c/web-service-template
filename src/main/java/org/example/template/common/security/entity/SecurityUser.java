package org.example.template.common.security.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户类，对应用户实体类，这里删除了不必要的一些字段，单独设置此类是为了不耦合进acl模块下的User类
 */
@Data
public class SecurityUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String username;
    private String password;
}
