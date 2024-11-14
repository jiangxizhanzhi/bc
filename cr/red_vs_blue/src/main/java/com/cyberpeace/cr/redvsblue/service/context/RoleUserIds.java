package com.cyberpeace.cr.redvsblue.service.context;

import java.util.List;

import lombok.Data;

/**
 * 角色、用户id集合
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class RoleUserIds {

	/**
	 * 岗位id列表
	 */
	List<String> roleIds;

	/**
	 * 用户id列表
	 */
	List<String> userIds;

}
