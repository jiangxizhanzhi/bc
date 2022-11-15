package com.cyberpeace.bs.service.bo.simulator;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 创建仪表的请求
 * @author SN
 */
@Data
public class UserSimulatorAddReq implements Serializable {

	@ApiModelProperty(value = "用户行为模型id")
	private String userModelId;

	@ApiModelProperty(value = "用户行为变量")
	private String behaviorVariables;

	@ApiModelProperty(value = "行为设备名称")
	private String deviceName;

	@ApiModelProperty(value = "行为设备ip")
	private String deviceIp;

	@ApiModelProperty(value = "行为设备id")
	private String deviceId;

	@ApiModelProperty(value = "场景id")
	private String sceneId;
}
