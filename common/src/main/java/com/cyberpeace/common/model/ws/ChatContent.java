package com.cyberpeace.common.model.ws;

import lombok.Getter;
import lombok.Setter;

/**
 * 聊天内容
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class ChatContent {

	private ChatToContent to;

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
