package com.cyberpeace.common.model.ws;

import java.io.Serializable;
import java.util.Map;

import com.cyberpeace.common.util.LocalStringUtil;
import com.cyberpeace.common.util.ObjUtil;
import com.google.common.collect.Maps;

/**
 * websocket标记的基类。
 *
 * @author yinqingrong@date 2024/11/08
 */
public class WsMessageIdentifier implements Serializable {

	/**
	 * 需要发送消息的用户id
	 */
	private String userId;

	/**
	 * 路径
	 */
	private String path;

	/**
	 * 获取业务id
	 * @return
	 */
	public String getIdentifierId() {
		Map<String, Object> maps = ObjUtil.obj2map(this);

		// 用户不参与业务字段排序
		maps.remove("userId");

		Map<String, Object> internal = Maps.newTreeMap();
		// 前端传入ws连接数据时，参数使用下划线形式
		// 为了得到一致的排序结果，也使用下划线
		for (Map.Entry<String, Object> item : maps.entrySet()) {
			internal.put(LocalStringUtil.snakeCaseFormat(item.getKey()), item.getValue());
		}

		StringBuilder stringBuilder = new StringBuilder();
		for (Map.Entry<String, Object> item : internal.entrySet()) {
			stringBuilder.append(item.getValue()).append("&");
		}

		return stringBuilder.toString();
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
