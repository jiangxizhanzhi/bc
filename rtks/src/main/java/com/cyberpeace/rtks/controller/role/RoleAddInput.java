package com.cyberpeace.rtks.controller.role;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 岗位新增输入
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see RoleInput
 * @since 1.0.0
 */
@Data
public class RoleAddInput extends RoleInput {

	/**
	 * 岗位名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "岗位名称")
	@NotBlank
	private String name;

	/**
	 * 岗位所属层级id
	 *
	 * @see String
	 */
	@NotBlank
	@ApiModelProperty(value = "岗位所属层级id")
	private String levelId;

}
