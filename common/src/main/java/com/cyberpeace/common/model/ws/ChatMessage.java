package com.cyberpeace.common.model.ws;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 聊天消息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

	/**
	 * 用户名
	 *
	 * @see String
	 */
	private String username;

	/**
	 * 化身
	 *
	 * @see String
	 */
	private String avatar;

	/**
	 * 接收者看到的聊天框(群聊为群id，私聊为发送者id)
	 *
	 * @see String
	 */
	private String id;

	/**
	 * 类型
	 *
	 * @see String
	 */
	private String type;

	/**
	 * 内容
	 *
	 * @see String
	 */
	private String content;

	/**
	 * CID
	 *
	 * @see Integer
	 */
	private final Integer cid = 0;

	/**
	 * 矿山
	 *
	 * @see Boolean
	 */
	private final Boolean mine = Boolean.FALSE;

	/**
	 * fromid
	 *
	 * @see String
	 */
	private String fromid;

	/**
	 * 时间
	 *
	 * @see String
	 */
	private String time;

	/**
	 * 时间戳
	 */
	private long timestamp;

	public void setTimestamp() {
		timestamp = System.currentTimeMillis();
	}

	/**
	 * uuid
	 *
	 * @see String
	 */
	private String uuid;
}
