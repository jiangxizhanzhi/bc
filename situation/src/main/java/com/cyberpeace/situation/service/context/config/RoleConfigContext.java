package com.cyberpeace.situation.service.context.config;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class RoleConfigContext implements Serializable {

	@ApiModelProperty(value = "红蓝对抗角色")
	private String rbRole;

}
