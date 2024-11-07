package com.cyberpeace.rtks.controller.role;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色新增输入
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class RoleAddInput extends RoleInput {

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "岗位名称")
	@NotBlank
	private String name;

	/**
	 * 水平id
	 */
	@NotBlank
	@ApiModelProperty(value = "岗位所属层级id")
	private String levelId;

}
