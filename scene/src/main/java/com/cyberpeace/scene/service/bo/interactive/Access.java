package com.cyberpeace.scene.service.bo.interactive;

import lombok.Data;

@Data
public class Access {

	private String protocol;

	private String username;

	private String password;

	private Integer port;

	private String host;

	private Integer hostPort;
}
