package com.cyberpeace.cr.crbase.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 任务完成数
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class MissionCompletion {

	/**
	 * 任务状态
	 */
	@ApiModelProperty(value = "任务状态")
	private Integer missionStatus;

	/**
	 * 完成数量
	 */
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
