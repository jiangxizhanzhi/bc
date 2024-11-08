package com.cyberpeace.oj.ojbase.service.bo;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;

import lombok.Data;

/**
 * 课堂 WS 消息标识符
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class ClassroomWsMessageIdentifier extends WsMessageIdentifier {

	/**
	 * 课堂id
	 */
	private String classroomId;

}
