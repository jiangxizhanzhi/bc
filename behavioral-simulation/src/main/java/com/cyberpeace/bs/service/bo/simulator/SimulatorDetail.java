package com.cyberpeace.bs.service.bo.simulator;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

import com.cyberpeace.bs.service.bo.behavior.BehaviorInstance;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SimulatorDetail implements Serializable {

	@ApiModelProperty(value = "id")
	private String resourceId;

	@ApiModelProperty(value = "场景名称")
	private String sceneName;

	@ApiModelProperty(value = "仪表ip")
	private String deviceIp;

	@ApiModelProperty(value = "用户数")
	private Integer userCount;

	@ApiModelProperty(value = "访问习惯")
	private List<String> accessHabit;

	@ApiModelProperty(value = "用户类型")
	private List<String> userTypes;

	@ApiModelProperty(value = "访问开始时间")
	private LocalTime startTime;

	@ApiModelProperty(value = "访问结束时间")
	private LocalTime endTime;

	@ApiModelProperty(value = "执行设备名称")
	private String deviceName;

	@ApiModelProperty(value = "行为列表")
	private List<BehaviorInstance> behaviors;
}
