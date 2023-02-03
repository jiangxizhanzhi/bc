package com.cyberpeace.bs.service.bo.monitor;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

import com.cyberpeace.bs.dao.entity.UserSimulator;
import com.cyberpeace.common.util.JsonUtil;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MonitorSimulatorDetail implements Serializable {

    @ApiModelProperty(value = "仪表resourceId")
    private String resourceId;

    @ApiModelProperty(value = "仪表名称")
    private String name;

	@ApiModelProperty(value = "试验任务名称")
	private String sceneName;

	@ApiModelProperty(value = "用户数")
	private Integer userCount;

    @ApiModelProperty(value = "用户类型")
	private List<String> userTypes;

    @ApiModelProperty(value = "运行设备")
    private String deviceName;

	@ApiModelProperty(value = "仪表地址")
	private String deviceIp;

    @ApiModelProperty(value = "访问习惯")
    private List<String> accessHabit;

    @ApiModelProperty(value = "访问开始时间")
    private LocalTime startTime;

    @ApiModelProperty(value = "访问结束时间")
    private LocalTime endTime;

    @ApiModelProperty(value = "状态")
    private Integer status;

    public void load(UserSimulator simulator) {
        this.resourceId = simulator.getResourceId();
		this.name = "用户行为仪表" + "_" + simulator.getResourceId();
		this.sceneName = simulator.getSceneName();
		this.userCount = simulator.getUserCount();
		this.userTypes = JsonUtil.toJsonObjectArray(simulator.getUserTypes(), String.class);
        this.deviceName = simulator.getDeviceName();
		this.deviceIp = simulator.getDeviceIp();
        this.accessHabit = JsonUtil.toJsonObjectArray(simulator.getAccessHabit(), String.class);
        this.startTime = simulator.getStartTime();
        this.endTime = simulator.getEndTime();
        this.status = simulator.getStatus();
    }
}
