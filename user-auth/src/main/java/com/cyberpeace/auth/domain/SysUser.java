package com.cyberpeace.auth.domain;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

/**
 * 用户表
 * @TableName sys_user
 */
@TableName(value ="sys_user")
@Data
public class SysUser extends BaseTable implements Serializable {

	@TableId(type = IdType.ASSIGN_UUID, value = "resource_id")
	private String resourceId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 姓名
     */
    private String name;

    /**
     * 密码
     */
    private String password;

    /**
     * 账号是否可用。默认为1（可用）0：禁用
     */
    private Integer enabled;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 是否管理员。0：非管理员，1：管理员
     */
    private Boolean administrator;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}