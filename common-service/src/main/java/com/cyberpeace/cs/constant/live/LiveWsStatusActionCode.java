package com.cyberpeace.cs.constant.live;

import lombok.Data;

/**
 * 直播状态代号
 * @author SN
 * @date 2024/05/06
 */
@Data
public final class LiveWsStatusActionCode {

	/**
	 * 开播
	 */
	public static final Integer PUBLISH = 1;

	/**
	 * 下播
	 */
	public static final Integer UNPUBLISH = 2;

	private LiveWsStatusActionCode() {}

}
