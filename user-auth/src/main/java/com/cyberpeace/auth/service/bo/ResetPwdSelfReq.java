package com.cyberpeace.auth.service.bo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ResetPwdSelfReq {

	@ApiModelProperty(value = "旧密码")
	private String oldPwd;

	@ApiModelProperty(value = "新密码")
	private String newPwd;
}
