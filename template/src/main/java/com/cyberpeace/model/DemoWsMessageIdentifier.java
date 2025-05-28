package com.cyberpeace.model;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import lombok.Builder;
import lombok.Data;

/**
 * ws消息标识的demo。
 * <p>
 * <p>
 * <p>
 * 声明的字段和请求ws连接时的字段一致！
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see WsMessageIdentifier
 * @since 1.0.0
 */
@Builder
@Data
public class DemoWsMessageIdentifier extends WsMessageIdentifier {

	/**
	 * 课时id
	 *
	 * @see String
	 */
	String lessonId;

	/**
	 * 课程id
	 *
	 * @see String
	 */
	String courseId;
}
