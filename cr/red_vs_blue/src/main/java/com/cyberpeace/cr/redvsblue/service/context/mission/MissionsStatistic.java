package com.cyberpeace.cr.redvsblue.service.context.mission;

import com.cyberpeace.cr.crbase.service.context.TaskCounter;

import lombok.Data;

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
}
