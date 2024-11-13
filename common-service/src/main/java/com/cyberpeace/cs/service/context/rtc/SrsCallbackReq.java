package com.cyberpeace.cs.service.context.rtc;

import lombok.Data;

/**
 * SRS回调请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class SrsCallbackReq {

	/**
	 * 行动
	 */
	private String action;

	/**
	 * 客户id
	 */
	private String clientId;

	/**
	 * 服务器id
	 */
	private String serverId;

	/**
	 * Serviceid
	 */
	private String serviceId;

	/**
	 * ip地址
	 */
	private String ip;

	/**
	 * 虚拟主机
	 */
	private String vhost;

	/**
	 * 应用程序
	 */
	private String app;

	/**
	 * 流
	 */
	private String stream;

	/**
	 * TC 网址
	 */
	private String tcUrl;

	/**
	 * 参数
	 */
	private String param;

	/**
	 * 流 URL
	 */
	private String streamUrl;

	/**
	 * 流id
	 */
	private String streamId;
}
