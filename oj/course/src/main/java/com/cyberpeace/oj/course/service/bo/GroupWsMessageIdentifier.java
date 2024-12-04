package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import lombok.Data;

/**
 * 组 WS 消息标识符
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see WsMessageIdentifier
 * @since 1.0.0
 */
@Data
public class GroupWsMessageIdentifier extends WsMessageIdentifier {

	/**
	 * 群id
	 *
	 * @see String
	 */
	private String groupId;

}
