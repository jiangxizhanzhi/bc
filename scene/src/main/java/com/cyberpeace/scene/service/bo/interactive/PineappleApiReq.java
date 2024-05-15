package com.cyberpeace.scene.service.bo.interactive;

import lombok.Data;

@Data
public class PineappleApiReq {

	private String module;

	private String action;

	public PineappleApiReq(String module, String action) {
		this.module = module;
		this.action = action;
	}
}
