package com.cyberpeace.rtks.dao.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("rtks_user_role")
public class UserRole extends BaseTable {

	/**
	 * 用户ID
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
	 * 岗位ID
	 */
    @ApiModelProperty("岗位ID")
    private String roleId;

	/**
	 * 岗位名称
	 */
    @ApiModelProperty("岗位名称")
    private String roleName;

}
