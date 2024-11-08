package com.cyberpeace.oj.ojbase.service.bo;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课程练习 WS 消息标识符
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonPracticeWsMessageIdentifier extends WsMessageIdentifier {

	/**
	 * 课id
	 */
	private String lessonId;

	/**
	 * 实践id
	 */
	private String practiceId;

	/**
	 * 从 Web
	 */
	private Boolean fromWeb;

	/**
	 * 共享
	 */
	private Integer shared;

}
