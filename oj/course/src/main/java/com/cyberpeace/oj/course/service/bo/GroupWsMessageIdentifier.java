package com.cyberpeace.oj.course.service.bo;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import lombok.Data;

/**
 * 组WS消息标识符
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class GroupWsMessageIdentifier extends WsMessageIdentifier {

	/**
	 * 组id
	 */
	private String groupId;

}
