package com.cyberpeace.cr.redvsblue.controller.req;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.cr.crbase.service.context.role.CrRoleNode;
import com.cyberpeace.cr.crbase.service.context.ColorMission;

import lombok.Data;

/**
 * 角色列表请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class RoleListReq<T> implements Serializable {

	/**
	 * id
	 *
	 * @see String
	 */
	private String Id;

	/**
	 * 资源id
	 *
	 * @see String
	 */
	private String resourceId;

	/**
	 * 名称
	 *
	 * @see String
	 */
	private String name;

	/**
	 * 描述
	 *
	 * @see String
	 */
	private String description;

	/**
	 * 角色组
	 *
	 * @see Integer
	 */
	private Integer roleGroup;

	/**
	 * 资产节点
	 *
	 * @see List<CrRoleNode>
	 */
	private List<CrRoleNode> assetNodes;

	/**
	 * 子级
	 *
	 * @see List<String>
	 */
	private List<String> children;

	/**
	 * 可见节点
	 *
	 * @see List<CrRoleNode>
	 */
	private List<CrRoleNode> visibleNodes;

	/**
	 * 钥匙
	 *
	 * @see String
	 */
	private String key;

	/**
	 * 用户
	 *
	 * @see String
	 */
	private String users;

	/**
	 * 类型
	 *
	 * @see String
	 */
	private String type;

	/**
	 * 任务计划
	 *
	 * @see ColorMission<T>
	 */
	private ColorMission<T> missionsScheme;

}
