package com.cyberpeace.oj.rtks.controller.role;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class RoleModifyInput extends RoleInput {

	/**
	 * 修改时要求有resource_id字段
	 * */
	@NotBlank
	private String resourceId;

}
