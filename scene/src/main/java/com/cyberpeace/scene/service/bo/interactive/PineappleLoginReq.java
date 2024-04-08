package com.cyberpeace.scene.service.bo.interactive;

import lombok.Data;

@Data
public class PineappleLoginReq {

	private String system = "";

	private String action = "login";

	private String username = "root";

	private String password = "root";

	private long time;

	public PineappleLoginReq() {
		this.time = System.currentTimeMillis() / 1000;
	}
}
