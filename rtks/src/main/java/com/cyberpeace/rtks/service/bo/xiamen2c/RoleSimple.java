package com.cyberpeace.rtks.service.bo.xiamen2c;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RoleSimple implements Serializable {

	@ApiModelProperty(value = "岗位id")
	private String resourceId;

	@ApiModelProperty(value = "岗位名称")
	private String name;
}
