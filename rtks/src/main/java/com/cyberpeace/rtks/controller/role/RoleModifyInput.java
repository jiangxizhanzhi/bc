package com.cyberpeace.rtks.controller.role;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.rtks.controller.NotEmptyString;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleModifyInput extends RoleInput {

	/**
	 * 修改时要求有resource_id字段
	 * */
	@NotBlank
	private String resourceId;

	@ApiModelProperty(value = "岗位名称")
	@NotEmptyString
	private String name;

	@ApiModelProperty(value = "岗位所属层级id")
	@NotEmptyString
	private String levelId;

}
