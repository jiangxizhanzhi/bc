package com.cyberpeace.cs.service.context.rtc;

import lombok.Data;

@Data
public class SrsCallbackReq {

	private String action;

	private String clientId;

	private String serverId;

	private String serviceId;

	private String ip;

	private String vhost;

	private String app;

	private String stream;

	private String tcUrl;

	private String param;

	private String streamUrl;

	private String streamId;
}
