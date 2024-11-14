package com.cyberpeace.rtks.service.bo.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleContext implements Serializable {

	/**
	 * 岗位id
	 */
	@ApiModelProperty("岗位id")
	private String roleId;

	/**
	 * 岗位名称
	 */
	@ApiModelProperty("岗位名称")
	private String roleName;

	/**
	 * 合格人数
	 */
	@ApiModelProperty("合格人数")
	private Integer qualified;

	public RoleContext(String roleId, String roleName) {
		this.roleId = roleId;
		this.roleName = roleName;
		this.qualified = 0;
	}

	public void addQualified() {
		this.qualified++;
	}
}