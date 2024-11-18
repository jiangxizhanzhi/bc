package com.cyberpeace.common.model.ws;

import java.util.Map;

import com.google.common.collect.Maps;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * websocket连接需要的上下文
 *
 * @author yinqingrong
 * @version 1.0.0
 * @date 2024/11/07
 * @see WsMessageIdentifier
 * @since 1.0.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebSocketConnectionContext extends WsMessageIdentifier {

	/**
	 * 建立连接时的原始请求信息
	 *
	 * @see Map<String, String>
	 */
	private Map<String, String> originParams = Maps.newTreeMap();

	/**
	 * 获取业务id
	 * @return
	 */
	@Override
	public String getIdentifierId() {
		StringBuilder stringBuilder = new StringBuilder();
		for (Map.Entry<String, String> item : originParams.entrySet()) {
			stringBuilder.append(item.getValue()).append("&");
		}

		return stringBuilder.toString();
	}
}
