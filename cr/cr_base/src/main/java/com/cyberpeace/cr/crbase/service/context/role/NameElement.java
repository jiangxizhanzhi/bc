package com.cyberpeace.cr.crbase.service.context.role;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NameElement {

	@ApiModelProperty(value = "用户姓名")
	private String name;
}
