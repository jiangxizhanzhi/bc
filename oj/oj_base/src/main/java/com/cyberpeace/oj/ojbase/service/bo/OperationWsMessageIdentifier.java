package com.cyberpeace.oj.ojbase.service.bo;

import com.cyberpeace.common.model.ws.WsMessageIdentifier;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 实操WS消息标识符
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OperationWsMessageIdentifier extends WsMessageIdentifier {

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
