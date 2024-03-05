package com.cyberpeace.scene.service.bo.interactive;

import lombok.Data;

@Data
public class ConnectionParam {

	private String protocol = "rdp";

	private String hostname;

	private String username;

	private String password;

	private String port;
}
