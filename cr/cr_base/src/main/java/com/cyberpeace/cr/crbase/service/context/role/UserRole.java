package com.cyberpeace.cr.crbase.service.context.role;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 用户角色
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
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

}
