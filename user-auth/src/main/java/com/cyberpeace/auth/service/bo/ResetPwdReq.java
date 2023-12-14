package com.cyberpeace.auth.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ResetPwdReq {

	@ApiModelProperty(value = "用户id")
	private String userId;

	@ApiModelProperty(value = "新密码")
	private String newPwd;
}
