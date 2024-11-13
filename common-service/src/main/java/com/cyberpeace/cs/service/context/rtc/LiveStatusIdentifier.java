package com.cyberpeace.cs.service.context.rtc;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;

import lombok.Data;

/**
 * 直播状态标识符
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class LiveStatusIdentifier extends WsMessageIdentifier {

	/**
	 * 课堂id
	 */
	private String stream;
}
