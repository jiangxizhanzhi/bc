package com.cyberpeace.rtks.service.bo.resource;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 角色资源
 *
 * @author SN
 */
@Data
public class RoleResource implements Serializable {

	private String name;

	private String description;

	/**
	 * 角色权限
	 */
	private List<String> targets;
}
