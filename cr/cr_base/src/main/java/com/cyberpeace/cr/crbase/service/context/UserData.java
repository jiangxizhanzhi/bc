package com.cyberpeace.cr.crbase.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserData {

	@ApiModelProperty(value = "头像")
	private String logo;

	@ApiModelProperty(value = "姓名")
	private String repName;

	@ApiModelProperty(value = "编号")
	private String resourceId;
}
