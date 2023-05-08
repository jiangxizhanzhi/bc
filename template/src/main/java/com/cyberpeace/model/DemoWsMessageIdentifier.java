package com.cyberpeace.model;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import lombok.Builder;
import lombok.Data;

/**
 * 演示ws消息标识符
 * ws消息标识的demo。
 * <p>
 * 声明的字段和请求ws连接时的字段一致！
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Builder
@Data
public class DemoWsMessageIdentifier extends WsMessageIdentifier {

	String lessonId;

	String courseId;
}
