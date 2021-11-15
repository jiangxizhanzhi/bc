package com.cyberpeace.cr.crbase.service.context;

import lombok.Data;

@Data
public class MissionCompletion {

	private Integer missionStatus;

	private Integer completeNum;

	public void addCompletionNum() {
		completeNum += 1;
	}

	public MissionCompletion(Integer missionStatus, Integer completeNum) {
		this.missionStatus = missionStatus;
		this.completeNum = completeNum;
	}

}
