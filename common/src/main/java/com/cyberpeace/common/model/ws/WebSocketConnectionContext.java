package com.cyberpeace.common.model.ws;

import com.google.common.collect.Maps;
import lombok.*;

import java.util.Map;

/**
 * websocket连接需要的上下文
 *
 * @author yinqingrong@date 2024/11/08
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WebSocketConnectionContext extends WsMessageIdentifier {

	/**
	 * 建立连接时的原始请求信息
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
