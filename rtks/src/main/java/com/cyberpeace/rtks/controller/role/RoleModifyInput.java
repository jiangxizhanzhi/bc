package com.cyberpeace.rtks.controller.role;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.rtks.controller.NotEmptyString;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 岗位修改输入
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see RoleInput
 * @since 1.0.0
 */
@Data
public class RoleModifyInput extends RoleInput {

	/**
	 * 修改时要求有resource_id字段
	 *
	 * @see String
	 */
	@NotBlank
	private String resourceId;

	/**
	 * 岗位名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "岗位名称")
	@NotEmptyString
	private String name;

	/**
	 * 岗位所属层级id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "岗位所属层级id")
	@NotEmptyString
	private String levelId;

}
