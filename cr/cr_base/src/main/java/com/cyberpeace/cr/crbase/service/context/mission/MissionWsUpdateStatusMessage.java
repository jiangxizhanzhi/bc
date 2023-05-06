package com.cyberpeace.cr.crbase.service.context.mission;

import static com.cyberpeace.cr.crbase.constant.MissionWsCode.MISSION_WS_UPDATE_STATUS;

import java.io.Serializable;

import lombok.Data;

/**
 *
 * 任务ws推送状态更新
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class MissionWsUpdateStatusMessage implements Serializable {

	private Integer code = MISSION_WS_UPDATE_STATUS;

	private Integer runningStatus;

	public MissionWsUpdateStatusMessage(Integer runningStatus) {
		this.runningStatus = runningStatus;
	}
}
