package com.cyberpeace.common.exception;

/**
 * 聊天异常
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see BusinessException
 * @since 1.0.0
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
