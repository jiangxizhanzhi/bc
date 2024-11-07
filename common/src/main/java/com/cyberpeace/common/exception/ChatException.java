package com.cyberpeace.common.exception;

/**
 * 聊天异常
 *
 * @author SN
 * @date 2024/11/07
 */
public class ChatException extends BusinessException {

	/**
	 * 聊天异常
	 *
	 * @param message 消息
	 */
	public ChatException(String message) {
		super(message);
	}
}
