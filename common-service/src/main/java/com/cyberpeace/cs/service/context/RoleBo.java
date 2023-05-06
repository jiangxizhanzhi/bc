package com.cyberpeace.cs.service.context;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 角色
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class RoleBo implements Serializable {

	private String teamName;

	private List<String> roleName;

	public RoleBo(String teamName, List<String> roleName) {
		this.teamName = teamName;
		this.roleName = roleName;
	}

	public RoleBo() {}
}
