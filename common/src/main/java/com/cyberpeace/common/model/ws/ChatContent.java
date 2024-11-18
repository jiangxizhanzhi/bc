package com.cyberpeace.common.model.ws;

import lombok.Data;

/**
 * 聊天内容
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
public class ChatContent {

	/**
	 * 自
	 *
	 * @see ChatToContent
	 */
	private ChatToContent to;

	/**
	 * 矿山
	 *
	 * @see ChatMineContent
	 */
	private ChatMineContent mine;

	/**
	 * is 组
	 *
	 * @return boolean
	 */
	public boolean isGroup() {
		return to.getType().equals("group");
	}

	/**
	 * 获取群id
	 *
	 * @return {@code String }
	 */
	public String getGroupId() {
		return to.getId();
	}

	/**
	 * 获取接收器id
	 *
	 * @return {@code String }
	 */
	public String getReceiverId() {
		return to.getId();
	}

	/**
	 * 获取矿山id
	 *
	 * @return {@code String }
	 */
	public String getMineId() {
		return mine.getId();
	}

}
