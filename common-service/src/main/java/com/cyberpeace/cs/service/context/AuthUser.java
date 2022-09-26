package com.cyberpeace.cs.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 授权的用户
 */
@Data
public class AuthUser {

	@ApiModelProperty(value = "用户id，resource_id")
	private String id;

	@ApiModelProperty(value = "姓名")
	private String name;

	@ApiModelProperty(value = "组织id")
	private String organization;

	@ApiModelProperty(value = "组织名称")
	private String organizationNameAll;

	@ApiModelProperty(value = "用户名")
	private String username;

}
