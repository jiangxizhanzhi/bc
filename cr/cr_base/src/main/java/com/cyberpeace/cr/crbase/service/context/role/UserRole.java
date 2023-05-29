package com.cyberpeace.cr.crbase.service.context.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("单个用户角色")

public class UserRole {

	@ApiModelProperty("用户id")
	private String resourceId;

	@ApiModelProperty("用户名")
	private String name;

	@ApiModelProperty("用户头像")
	private String logo;

	@ApiModelProperty("角色")
	private String roleName;

	@ApiModelProperty("角色描述")
	private String roleDesc;

	@ApiModelProperty("角色组：4红方 5蓝方")
	private Integer type;
}
