package com.cyberpeace.cr.redvsblue.controller.rsp;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.crbase.service.context.UserData;
import com.google.common.collect.Lists;
import lombok.Data;

/**
 * RB房间角色结果
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class RbRoomRoleRes implements Serializable {

	/**
	 * 资源id
	 */
	private String resourceId;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 角色组
	 */
	private Integer roleGroup;

	/**
	 * 用户
	 */
	private List<String> users;

	/**
	 * 用户数据
	 */
	private List<UserData> usersData;

	/**
	 * 子级
	 */
	private List<RbRoomRoleRes> children = Lists.newArrayList();

}
