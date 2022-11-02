package com.cyberpeace.bs.service.bo.traffic;

import java.io.Serializable;

import com.cyberpeace.bs.service.bo.behavior.UserBehaviorBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserTrafficBo extends UserBehaviorBo implements Serializable {

	@ApiModelProperty(value = "执行状态")
	private Integer status;

	@ApiModelProperty(value = "执行设备id")
	private String targetId;

	@ApiModelProperty(value = "执行设备名称")
	private String targetName;

}
