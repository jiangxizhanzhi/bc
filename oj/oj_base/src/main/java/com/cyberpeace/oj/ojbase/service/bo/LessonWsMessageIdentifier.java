package com.cyberpeace.oj.ojbase.service.bo;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课时WS消息标识符
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonWsMessageIdentifier extends WsMessageIdentifier {

	/**
	 * 课时id
	 */
	private String lessonId;

	/**
	 * 共享
	 */
	private Boolean shared;

	/**
	 * 从Web
	 */
	private Boolean fromWeb;

}
