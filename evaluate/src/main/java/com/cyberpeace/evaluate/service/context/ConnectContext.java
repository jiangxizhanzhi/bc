package com.cyberpeace.evaluate.service.context;

import lombok.Data;

@Data
public class ConnectContext {

	private String sourceNodeId;

	private String targetNodeId;

	private String label;

}
