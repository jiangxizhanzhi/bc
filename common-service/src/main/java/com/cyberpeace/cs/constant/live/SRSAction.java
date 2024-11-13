package com.cyberpeace.cs.constant.live;

import lombok.Data;

/**
 * SRS操作
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public final class SRSAction {

	/**
	 * 发布时
	 */
	public static final String ON_PUBLISH = "on_publish";

	/**
	 * 取消发布时
	 */
	public static final String ON_UNPUBLISH = "on_unpublish";

	/**
	 * SRS操作
	 */
	private SRSAction() {}

}
