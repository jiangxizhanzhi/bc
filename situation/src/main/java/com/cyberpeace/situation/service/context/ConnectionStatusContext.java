package com.cyberpeace.situation.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class ConnectionStatusContext extends StatusContext {

	/**
	 * 链路id
	 */
	@ApiModelProperty("链路id")
	private String connectionId;

}
