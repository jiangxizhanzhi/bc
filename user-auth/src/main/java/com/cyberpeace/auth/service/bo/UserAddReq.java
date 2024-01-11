package com.cyberpeace.auth.service.bo;

import com.cyberpeace.auth.util.UserValidator;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.util.CrCommonValidator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

@Data
public class UserAddReq {

	@ApiModelProperty(value = "用户名", name = "username")
	@JsonProperty(value = "username")
	private String username;

	@ApiModelProperty(value = "姓名")
	private String name;

	@ApiModelProperty(value = "密码")
	private String password;

	@ApiModelProperty(value = "头像")
	private String avatar;

	@ApiModelProperty(value = "是否管理员。false：非管理员，true：管理员")
	private Boolean admin = false;

	@ApiModelProperty(value = "过期时间")
	private Date expireTime;

	@ApiModelProperty(value = "备注")
	private String notes;

	public void validate(){
		this.validatePwd();
		CrCommonValidator.validateNameLen(this.username, ErrorCode.NAME_DUPLICATE);
	}

	protected void validatePwd(){
		UserValidator.validatePassword(this.password);
	}
}
