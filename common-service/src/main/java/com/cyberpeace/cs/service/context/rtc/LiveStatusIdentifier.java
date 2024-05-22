package com.cyberpeace.cs.service.context.rtc;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;

import lombok.Data;

@Data
public class LiveStatusIdentifier extends WsMessageIdentifier {

	/**
	 * 课堂id
	 */
	private String stream;
}
