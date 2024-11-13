package com.cyberpeace.cs.service.context.rtc;

import lombok.Data;

/**
 * SRS Api结果
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class SrsApiRes {

	/**
	 * 代号
	 */
	private String code;

	/**
	 * 服务器
	 */
	private String server;

	/**
	 * PID
	 */
	private String pid;
}
