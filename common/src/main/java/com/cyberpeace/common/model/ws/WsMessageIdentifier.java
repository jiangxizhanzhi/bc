package com.cyberpeace.common.model.ws;

import java.io.Serializable;
import java.util.Map;

import com.cyberpeace.common.util.LocalStringUtil;
import com.cyberpeace.common.util.ObjUtil;
import com.google.common.collect.Maps;

/**
 * ws消息标识符
 * websocket标记的基类。
 *
 * @author yinqingrong
 * @date 2023/05/06
 */
public class WsMessageIdentifier implements Serializable {

	/**
	 * 需要发送消息的用户id
	 */
	private String userId;

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

	/**
	 * 得到用户id
	 *
	 * @return {@link String}
	 */
	public String getUserId() {
		return userId;
	}

	/**
	 * 设置用户id
	 *
	 * @param userId 用户id
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}

	/**
	 * 获取路径
	 *
	 * @return {@link String}
	 */
	public String getPath() {
		return path;
	}

	/**
	 * 设置路径
	 *
	 * @param path 路径
	 */
	public void setPath(String path) {
		this.path = path;
	}
}
