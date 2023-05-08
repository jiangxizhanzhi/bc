package com.cyberpeace.bs.service.bo.monitor;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.List;

import com.cyberpeace.bs.dao.entity.UserSimulator;
import com.cyberpeace.common.util.JsonUtil;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 仪表监控数据详情
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class MonitorSimulatorDetail implements Serializable {

    /**
     * 资源id
     */
    @ApiModelProperty(value = "仪表resourceId")
    private String resourceId;

    /**
     * 名称
     */
    @ApiModelProperty(value = "仪表名称")
    private String name;

    /**
     * 场景名称
     */
    @ApiModelProperty(value = "试验任务名称")
	private String sceneName;

    /**
     * 用户数量
     */
    @ApiModelProperty(value = "用户数")
	private Integer userCount;

    /**
     * 用户类型
     */
    @ApiModelProperty(value = "用户类型")
	private List<String> userTypes;

    /**
     * 运行设备名称
     */
    @ApiModelProperty(value = "运行设备")
    private String deviceName;

    /**
     * 仪表设备ip地址
     */
    @ApiModelProperty(value = "仪表地址")
	private String deviceIp;

    /**
     * 访问习惯
     */
    @ApiModelProperty(value = "访问习惯")
    private List<String> accessHabit;

    /**
     * 开始时间
     */
    @ApiModelProperty(value = "访问开始时间")
    private LocalTime startTime;

    /**
     * 结束时间
     */
    @ApiModelProperty(value = "访问结束时间")
    private LocalTime endTime;

    /**
     * 状态
     */
    @ApiModelProperty(value = "状态")
    private Integer status;

    /**
     * 从仪表复制详情数据
     *
     * @param simulator 仪表
     */
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
