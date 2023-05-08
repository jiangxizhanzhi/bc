package com.cyberpeace.common.exception;

/**
 * 聊天异常
 *
 * @author cyberpeace
 * @date 2023/05/06
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
