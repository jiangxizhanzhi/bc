package com.cyberpeace.cr.crbase.service.context.mission;

import java.io.Serializable;

import com.cyberpeace.cr.crbase.constant.MissionWsCode;

import lombok.Data;

/**
 * 任务ws推送状态更新
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class MissionWsUpdateStatusMessage implements Serializable {

	/**
	 * 代号
	 */
	private Integer code = MissionWsCode.MISSION_WS_UPDATE_STATUS;

	/**
	 * 运行状态
	 */
	private Integer runningStatus;

	public MissionWsUpdateStatusMessage(Integer runningStatus) {
		this.runningStatus = runningStatus;
	}
}
