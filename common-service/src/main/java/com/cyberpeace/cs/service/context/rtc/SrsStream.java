package com.cyberpeace.cs.service.context.rtc;

import lombok.Data;

@Data
public class SrsStream {

	private String id;

	private String name;

	private String vhost;

	private String app;

	private String tcUrl;

	private String url;

	private Long liveMs;

	private Integer clients;

	private Integer frames;

	private Integer sendBytes;

	private Integer recvBytes;

	private Object kbps;

	private Object publish;

	private Object video;

	private Object audio;
}
