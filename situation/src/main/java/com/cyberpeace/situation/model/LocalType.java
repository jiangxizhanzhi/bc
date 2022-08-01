package com.cyberpeace.situation.model;

import lombok.Data;

@Data
public class LocalType {

	private String code;

	private String msg;

	public LocalType(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}
}
