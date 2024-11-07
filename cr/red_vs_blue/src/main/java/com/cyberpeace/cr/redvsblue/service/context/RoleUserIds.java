package com.cyberpeace.cr.redvsblue.service.context;

import java.util.List;

import lombok.Data;

/**
 * 角色、用户id集合
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class RoleUserIds {

	/**
	 * 角色id列表
	 */
	List<String> roleIds;

	/**
	 * 用户id列表
	 */
	List<String> userIds;

}
