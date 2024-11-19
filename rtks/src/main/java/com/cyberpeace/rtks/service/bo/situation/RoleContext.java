package com.cyberpeace.rtks.service.bo.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 岗位上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class RoleContext implements Serializable {

	/**
	 * 岗位id
	 *
	 * @see String
	 */
	@ApiModelProperty("岗位id")
	private String roleId;

	/**
	 * 岗位名称
	 *
	 * @see String
	 */
	@ApiModelProperty("岗位名称")
	private String roleName;

	/**
	 * 合格人数
	 *
	 * @see Integer
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