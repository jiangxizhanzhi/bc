package com.cyberpeace.oj.ojbase.service.bo;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 课时练习WS消息标识符
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonPracticeWsMessageIdentifier extends WsMessageIdentifier {

	/**
	 * 课时id
	 */
	private String lessonId;

	/**
	 * 练习id
	 */
	private String practiceId;

	/**
	 * 从Web
	 */
	private Boolean fromWeb;

	/**
	 * 共享
	 */
	private Integer shared;

}
