package com.cyberpeace.common.model.ws;

import java.io.Serializable;
import java.util.Map;

import com.cyberpeace.common.util.ObjUtil;

import lombok.Data;

/**
 * websocket标记的基类。
 *
 * @author yinqingrong
 */
@Data
public class WsMessageIdentifier implements Serializable {

	/**
	 * 需要发送消息的用户id
	 */
	private String userId;

	/**
	 * 获取业务id
	 * @return
	 */
	public String getBusinessId() {
		Map<String, Object> maps = ObjUtil.obj2map(this);

		maps.remove("userId");

		StringBuilder stringBuilder = new StringBuilder();
		for (Map.Entry<String, Object> item : maps.entrySet()) {
			stringBuilder.append(item.getValue()).append("&");
		}

		return stringBuilder.toString();
	}
}
