package com.cyberpeace.scene.service.bo.interactive;

import lombok.Data;

@Data
public class ConnectionRes {

	private String url;

	private String connectionId;

	private String connectionName;

	private boolean success;

	private String requestId;
}
