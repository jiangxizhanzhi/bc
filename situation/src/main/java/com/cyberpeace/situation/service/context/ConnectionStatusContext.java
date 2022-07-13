package com.cyberpeace.situation.service.context;

import lombok.Data;

@Data
public class ConnectionStatusContext extends StatusContext {

	/**
	 * 链路id
	 */
	private String connectionId;

}
