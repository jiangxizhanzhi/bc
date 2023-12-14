package com.cyberpeace.auth.service.bo;

import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.auth.util.UserValidator;

import io.swagger.annotations.ApiModelProperty;

@Data
public class UserModifyReq extends UserAddReq {

	@ApiModelProperty(value = "id")
	private String resourceId;

	@Override
	protected void validatePwd() {
		if (StringUtils.isNotBlank(this.getPassword())) {
			UserValidator.validatePassword(this.getPassword());
		}
	}

}
