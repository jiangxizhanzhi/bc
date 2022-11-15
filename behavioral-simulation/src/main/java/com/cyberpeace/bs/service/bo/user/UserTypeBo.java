package com.cyberpeace.bs.service.bo.user;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserTypeBo extends UserTypeAddBo implements Serializable {

	@ApiModelProperty(value = "uuid")
	private String resourceId;
}
