package com.cyberpeace.bs.service.bo.user;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserTypeAddBo implements Serializable {

	@ApiModelProperty(value = "名称")
	private String name;
}
