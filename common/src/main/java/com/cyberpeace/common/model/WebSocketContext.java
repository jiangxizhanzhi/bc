package com.cyberpeace.common.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * websocket连接需要的上下文
 * 
 * @author yinqingrong
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebSocketContext implements Serializable {

	/**
	 * 建立连接的用户id
	 */
	private String userId;

	/**
	 * 建立连接的业务id
	 */
	private String businessId;

	@Override
	public String toString() {
		return userId + "&" + businessId;
	}
}
