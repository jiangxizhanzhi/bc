package com.cyberpeace.rtks.controller.role;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.rtks.controller.NotEmptyString;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 角色修改输入
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class RoleModifyInput extends RoleInput {

	/**
	 * 修改时要求有resource_id字段
	 */
	@NotBlank
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "岗位名称")
	@NotEmptyString
	private String name;

	/**
	 * 水平id
	 */
	@ApiModelProperty(value = "岗位所属层级id")
	@NotEmptyString
	private String levelId;

}
