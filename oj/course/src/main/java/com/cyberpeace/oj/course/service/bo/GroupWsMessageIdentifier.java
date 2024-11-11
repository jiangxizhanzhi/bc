package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import lombok.Data;

/**
 * 组 WS 消息标识符
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class GroupWsMessageIdentifier extends WsMessageIdentifier {

	/**
	 * 群id
	 */
	private String groupId;

}
