package com.cyberpeace.common.model.user;

import java.io.Serializable;
import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

@Data
public class UserInfo implements Serializable {

    /**
     * 用户id，不要使用自增数字编号（必须）
     */
    private String userId;
    
	/**
	 * 姓名（文字显示）
	 */
	private String name;

	/**
	 * 用户名（对前端显示，登录使用）
	 */
    private String userName;

    /**
     * 头像地址
     */
    private String avatarUrl;

    /**
     * 组织id
     */
    private String organizationId;

    /**
     * 组织名
     */
    private String organizationData;

    /**
     * 用户角色(用来做权限校验，和rtks的岗位角色不相关)
     */
    private List<String> roles = Lists.newArrayList();

    private String positionId;

    private String positionName;

    /**
     * true表示用户为管理员
     */
    private boolean admin;

    /**
     * true表示用户是超级用户
     */
    private boolean superuser;

    /**
     * 岗位编号
     */
    private String roleId;

    /**
     * 组织结构信息
     */
    private List<String> orgpaths;

}
