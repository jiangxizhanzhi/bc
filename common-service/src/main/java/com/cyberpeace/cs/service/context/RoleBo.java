package com.cyberpeace.cs.service.context;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 岗位Bo
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class RoleBo implements Serializable {

	/**
	 * 团队名称
	 */
	private String teamName;

	/**
	 * 岗位名称
	 */
	private List<String> roleName;

	public RoleBo(String teamName, List<String> roleName) {
		this.teamName = teamName;
		this.roleName = roleName;
	}

	public RoleBo() {
	}
}
