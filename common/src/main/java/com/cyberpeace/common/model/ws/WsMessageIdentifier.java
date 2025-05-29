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
/**
 * 该代码定义了一个名为 `WsMessageIdentifier` 的 Java 类，用于生成 WebSocket 消息标识符。以下是代码的简要分析： 
 * 1. **类的功能**： 
 * - 该类实现了 `Serializable` 接口，表明它可以被序列化。 
 * - 它的主要功能是根据对象的属性生成一个唯一的业务标识符（`getIdentifierId` 方法）。 
 * 2. **关键属性**： 
 * - `userId`：表示需要发送消息的用户ID。 
 * - `path`：表示路径信息。 
 * 3. **核心方法**： 
 * - `getIdentifierId()`： 
 * - 将对象的所有字段转换为 `Map`。 
 * - 移除 `userId` 字段，因为它不参与业务字段的排序。 
 * - 使用 `LocalStringUtil.snakeCaseFormat` 将字段名转换为下划线格式，并按字典序排序。 
 * - 构造一个字符串，将字段值用 `&` 连接起来，返回作为业务标识符。 
 * 4. **工具类依赖**： 
 * - 使用了 `ObjUtil.obj2map` 将对象转换为 `Map`。 
 * - 使用了 `Maps.newTreeMap` 来确保字段按字典序排序。 
 * - 使用了 `LocalStringUtil.snakeCaseFormat` 对字段名进行格式化。 
 * 5. **设计特点**： 
 * - 通过移除 `userId` 和统一字段名格式，确保生成的标识符与业务逻辑相关联。 
 * - 使用 `StringBuilder` 提高字符串拼接效率。 
 * 总结：该类主要用于生成基于 WebSocket 消息的唯一业务标识符，适用于需要对多个字段进行组合并排序的场景。 
**/
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

