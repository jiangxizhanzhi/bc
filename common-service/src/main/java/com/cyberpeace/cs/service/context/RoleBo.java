package com.cyberpeace.cs.service.context;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 角色 bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class RoleBo implements Serializable {

	/**
	 * 团队名称
	 *
	 * @see String
	 */
	private String teamName;

	/**
	 * 角色名称
	 *
	 * @see List<String>
	 */
	private List<String> roleName;

	public RoleBo(String teamName, List<String> roleName) {
		this.teamName = teamName;
		this.roleName = roleName;
	}

	public RoleBo() {
	}
}
