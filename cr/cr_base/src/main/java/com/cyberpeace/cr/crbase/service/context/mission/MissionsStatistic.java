package com.cyberpeace.cr.crbase.service.context.mission;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.cr.crbase.service.context.TaskCounter;

import lombok.Data;
import lombok.Getter;

@Data
public class MissionsStatistic {

	private Integer roleGroup;

	private String userName;

	private String userResourceId;

	private Integer releaseMissionsCount;

	private Integer schemeMissionsCount;

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
