package com.cyberpeace.situation.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 连接状态上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see StatusContext
 * @since 1.0.0
 */
@Data
public class ConnectionStatusContext extends StatusContext {

	/**
	 * 链路id
	 *
	 * @see String
	 */
	@ApiModelProperty("链路id")
	private String connectionId;

}
