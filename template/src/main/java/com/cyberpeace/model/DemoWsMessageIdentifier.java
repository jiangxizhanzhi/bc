package com.cyberpeace.model;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import lombok.Builder;
import lombok.Data;

/**
 * ws消息标识的demo。
 * 声明的字段和请求ws连接时的字段一致！
 *
 * @author admin
 * @date 2024/11/12
 */
@Builder
@Data
public class DemoWsMessageIdentifier extends WsMessageIdentifier {

	/**
	 * 课时ID
	 */
	String lessonId;

	/**
	 * 课程id
	 */
	String courseId;
}
