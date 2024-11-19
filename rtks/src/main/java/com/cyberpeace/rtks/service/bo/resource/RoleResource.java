package com.cyberpeace.rtks.service.bo.resource;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 角色资源
 *
 * @author SN
 * @version 1.0.0
 * @date 2023/05/31
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class RoleResource implements Serializable {

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
	 * 角色权限
	 *
	 * @see List<String>
	 */
	private List<String> targets;
}
