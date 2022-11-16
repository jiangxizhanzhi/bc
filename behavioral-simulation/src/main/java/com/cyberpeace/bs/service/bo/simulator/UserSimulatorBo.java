package com.cyberpeace.bs.service.bo.simulator;

import java.io.Serializable;

import com.cyberpeace.bs.service.bo.model.UserModelBo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UserSimulatorBo extends UserModelBo implements Serializable {

	@ApiModelProperty(value = "执行状态")
	private Integer status;

	@ApiModelProperty(value = "执行设备id")
	private String deviceId;

	@ApiModelProperty(value = "执行设备名称")
	private String deviceName;

}
