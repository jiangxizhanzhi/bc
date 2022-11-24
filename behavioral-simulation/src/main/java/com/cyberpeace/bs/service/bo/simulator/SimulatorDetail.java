package com.cyberpeace.bs.service.bo.simulator;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.bs.service.bo.behavior.LcBehavior;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SimulatorDetail extends UserSimulatorBo implements Serializable {

	@ApiModelProperty(value = "场景名称")
	private String sceneName;

	@ApiModelProperty(value = "仪表ip")
	private String deviceIp;

	@ApiModelProperty(value = "行为列表")
	private List<LcBehavior> behaviors;
}
