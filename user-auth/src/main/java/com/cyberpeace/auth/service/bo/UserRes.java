package com.cyberpeace.auth.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UserRes {

	@ApiModelProperty(value = "id")
	private String resourceId;

	@ApiModelProperty(value = "用户名", name = "username")
	private String username;

	@ApiModelProperty(value = "姓名")
	private String name;

	@ApiModelProperty(value = "头像")
	private String avatar;

	@ApiModelProperty(value = "是否管理员。0：非管理员，1：管理员")
	private Boolean administrator;

	@ApiModelProperty(value = "是否禁用，0=禁用，1=可用")
	private Integer enabled;

	@ApiModelProperty(value = "过期时间")
	private Date expireTime;

	@ApiModelProperty(value = "备注")
	private String notes;
}
