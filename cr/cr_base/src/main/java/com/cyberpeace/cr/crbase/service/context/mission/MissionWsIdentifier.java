package com.cyberpeace.cr.crbase.service.context.mission;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;

import lombok.Data;

/**
 * 提交任务、提交评估时，发送ws消息，提醒前端重新从接口获取数据
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class MissionWsIdentifier extends WsMessageIdentifier {

	/**
	 * 业务id
	 */
	private String businessId;
}
