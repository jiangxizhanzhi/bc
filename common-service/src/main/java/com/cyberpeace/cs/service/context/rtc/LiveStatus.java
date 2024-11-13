package com.cyberpeace.cs.service.context.rtc;

import lombok.Data;

/**
 * 直播状态
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class LiveStatus {

	/**
	 * 网址
	 */
	private String url;

	/**
	 * 上直播
	 */
	private Boolean onLive;
}
