package com.cyberpeace.cs.service.context;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;

import lombok.Data;

/**
 * 聊天ws消息标识符
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class ChatWsMessageIdentifier extends WsMessageIdentifier {

	/** 聊天室业务编号 课堂=课堂表编号 红蓝=红蓝对抗房间编号*/
	private String businessId;

	/** 聊天室类型*/
	private Integer businessType;

}
