package com.cyberpeace.auth.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserRes {

	@ApiModelProperty(value = "id")
	private String resourceId;

	@ApiModelProperty(value = "用户名")
	private String username;

	@ApiModelProperty(value = "姓名")
	private String name;

	@ApiModelProperty(value = "头像")
	private String avatar;

	@ApiModelProperty(value = "是否管理员。0：非管理员，1：管理员")
	private Boolean administrator;

	@ApiModelProperty(value = "电话")
	private String phone;

	@ApiModelProperty(value = "邮箱")
	private String email;

	@ApiModelProperty(value = "是否在线")
	private Boolean online;
}
