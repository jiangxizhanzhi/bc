package com.cyberpeace.model;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import lombok.Builder;
import lombok.Data;

/**
 * ws消息标识的demo。
 *
 * 声明的字段和请求ws连接时的字段一致！
 */
@Builder
@Data
public class DemoWsMessageIdentifier extends WsMessageIdentifier {

	String lessonId;

	String courseId;
}
