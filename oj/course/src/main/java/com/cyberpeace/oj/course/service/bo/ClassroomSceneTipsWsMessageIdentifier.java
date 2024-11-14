package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import lombok.Data;

/**
 * 课堂场景技巧WS消息标识符
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class ClassroomSceneTipsWsMessageIdentifier extends WsMessageIdentifier {

	/**
	 * 房间id
	 */
	private String roomId;

}
