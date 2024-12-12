package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import lombok.Getter;
import lombok.Setter;

/**
 * 课堂场景技巧WS消息标识符
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see WsMessageIdentifier
 * @since 1.0.0
 */
@Getter
@Setter
public class ClassroomSceneTipsWsMessageIdentifier extends WsMessageIdentifier {

	/**
	 * 房间id
	 *
	 * @see String
	 */
	private String roomId;

}
