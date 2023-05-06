package com.cyberpeace.cr.crbase.service.context.mission;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.cr.crbase.service.context.TaskCounter;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;

/**
 *
 * 任务统计
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class MissionsStatistic {

	@ApiModelProperty(value = "角色组")
	private Integer roleGroup;

	@ApiModelProperty(value = "用户名")
	private String userName;

	@ApiModelProperty(value = "用户id")
	private String userResourceId;

	@ApiModelProperty(value = "下发任务数")
	private Integer releaseMissionsCount;

	@ApiModelProperty(value = "任务总数")
	private Integer schemeMissionsCount;

	@ApiModelProperty(value = "完成任务数")
	private Integer completeMissionsCount;

	public MissionsStatistic(Integer roleCode, String userId, TaskCounter taskCounter) {
		this.roleGroup = roleCode;
		this.userResourceId = userId;
		this.releaseMissionsCount = taskCounter.getReleased() + taskCounter.getFinished();
		this.schemeMissionsCount = taskCounter.getNotReleased().intValue()
				+ this.releaseMissionsCount;
		this.completeMissionsCount = taskCounter.getFinished();

	}

	public enum MissionStatisticalState implements GenericEnum {

		MISSION_NOT_STARTED(1, "未开始"), MISSION_ONGOING(2, "进行中"), MISSION_COMPLETED(3, "已完成");

		@Getter
		private Integer code;

		@Getter
		private final String desc;

		MissionStatisticalState(Integer code, String desc) {
			this.code = code;
			this.desc = desc;
		}

	}
}
