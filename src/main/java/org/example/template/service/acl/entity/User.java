package org.example.template.service.acl.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

/**
 * <p>
 * 用户
 * </p>
 *
 * @author lyj
 * @since 2023-08-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("acl_user")
@ApiModel(value="User对象", description="用户")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id;

    @ApiModelProperty(value = "用户名")
    @NotNull(message = "用户名不能为空")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotNull(message = "密码不能为空")
    private String password;

    @ApiModelProperty(value = "角色名")
    private String roleName;

    @ApiModelProperty(value = "逻辑删除，0为未删除，1为已删除，默认为0")
    @TableLogic
    private Boolean isDeleted;

    @ApiModelProperty(value = "创建时间")
    @TableField(fill = FieldFill.INSERT)
    private Date gmtCreated;

    @ApiModelProperty(value = "修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date gmtModified;


}
