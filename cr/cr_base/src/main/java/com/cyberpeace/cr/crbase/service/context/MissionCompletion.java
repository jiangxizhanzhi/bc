package com.cyberpeace.cr.crbase.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 *
 * 任务完成数
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class MissionCompletion {

	@ApiModelProperty(value = "任务状态")
	private Integer missionStatus;

	@ApiModelProperty(value = "任务完成数")
	private Integer completeNum;

	public void addCompletionNum() {
		completeNum += 1;
	}

	public MissionCompletion(Integer missionStatus, Integer completeNum) {
		this.missionStatus = missionStatus;
		this.completeNum = completeNum;
	}

}
