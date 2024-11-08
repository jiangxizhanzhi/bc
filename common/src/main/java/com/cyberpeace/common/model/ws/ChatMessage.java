package com.cyberpeace.common.model.ws;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 聊天消息
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

	/**
	 * 用户名
	 */
	private String username;

	/**
	 * 化身
	 */
	private String avatar;

	/**
	 * 接收者看到的聊天框(群聊为群id，私聊为发送者id)
	 */
	private String id;

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 内容
	 */
	private String content;

	/**
	 * CID
	 */
	private final Integer cid = 0;

	/**
	 * 矿山
	 */
	private final Boolean mine = Boolean.FALSE;

	/**
	 * fromid
	 */
	private String fromid;

	/**
	 * 时间
	 */
	private String time;

	/**
	 * 时间戳
	 */
	private long timestamp;

	/**
	 * 设置时间戳
	 */
	public void setTimestamp() {
		timestamp = System.currentTimeMillis();
	}

	/**
	 * uuid
	 */
	private String uuid;
}
