package com.cyberpeace.oj.ojbase.service.bo;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * 第 WS 课文标识符
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see WsMessageIdentifier
 * @since 1.0.0
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LessonWsMessageIdentifier extends WsMessageIdentifier {

	/**
	 * 课id
	 *
	 * @see String
	 */
	private String lessonId;

	/**
	 * 共享
	 *
	 * @see Boolean
	 */
	private Boolean shared;

	/**
	 * 从 Web
	 *
	 * @see Boolean
	 */
	private Boolean fromWeb;

}
