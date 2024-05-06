package com.cyberpeace.cs.service.context.rtc;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;

import lombok.Data;

@Data
public class LiveStatusIdentifier extends WsMessageIdentifier {

	private String stream;
}
