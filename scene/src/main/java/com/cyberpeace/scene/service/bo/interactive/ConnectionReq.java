package com.cyberpeace.scene.service.bo.interactive;

import lombok.Data;

@Data
public class ConnectionReq {

	private String username;

	private boolean flag = false;

	private ConnectionParam connection_params;

	public ConnectionReq(String username, String password, String hostname, Integer port) {
		ConnectionParam param = new ConnectionParam();
		param.setUsername(username);
		param.setPassword(password);
		param.setHostname(hostname);
		param.setPort("" + port);
		this.connection_params = param;
		this.username = "default";
	}
}
