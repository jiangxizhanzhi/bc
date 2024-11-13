package com.cyberpeace.cs.service.context.rtc;

import lombok.Data;

/**
 * SRS流
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class SrsStream {

	/**
	 * id
	 */
	private String id;

	/**
	 * 名称
	 */
	private String name;

	/**
	 * 虚拟主机
	 */
	private String vhost;

	/**
	 * 应用程序
	 */
	private String app;

	/**
	 * TC 网址
	 */
	private String tcUrl;

	/**
	 * 网址
	 */
	private String url;

	/**
	 * 直播
	 */
	private Long liveMs;

	/**
	 * 客户
	 */
	private Integer clients;

	/**
	 * 框架
	 */
	private Integer frames;

	/**
	 * 发送字节
	 */
	private Integer sendBytes;

	/**
	 * RECV 字节
	 */
	private Integer recvBytes;

	/**
	 * kbps
	 */
	private Object kbps;

	/**
	 * 发布
	 */
	private Object publish;

	/**
	 * 视频
	 */
	private Object video;

	/**
	 * 音频
	 */
	private Object audio;
}
