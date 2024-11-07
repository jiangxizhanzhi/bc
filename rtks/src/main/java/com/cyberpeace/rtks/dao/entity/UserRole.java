package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户角色
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@TableName("rtks_user_role")
public class UserRole extends BaseTable {

    /**
     * 用户id
     */
    @TableId(type = IdType.UUID, value = "user_id")
    @ApiModelProperty("用户ID")
    private String userId;

    /**
     * 用户名称
     */
    @ApiModelProperty("用户名称")
    private String userName;

    /**
     * 角色id
     */
    @ApiModelProperty("岗位ID")
    private String roleId;

    /**
     * 角色名称
     */
    @ApiModelProperty("岗位名称")
    private String roleName;

}
