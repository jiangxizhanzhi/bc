package com.cyberpeace.cr.crbase.service.context.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户昵称
 */
@Data
public class NameElement {

	@ApiModelProperty(value = "用户昵称")
	private String name;
}
