package com.cyberpeace.rtks.service.bo.resource;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 角色资源
 *
 * @author SN
 * @date 2023/05/31
 */
@Data
public class RoleResource implements Serializable {

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 描述
	 */
	private String description;

	/**
	 * 角色权限
	 */
	private List<String> targets;
}
