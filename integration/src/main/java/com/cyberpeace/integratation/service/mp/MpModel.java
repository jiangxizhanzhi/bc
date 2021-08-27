package com.cyberpeace.integratation.service.mp;

public class MpModel {
	public Object data;
	public String code;
	public String detail;
	public String message;

	public MpModel(Object data, String code, String detail, String message) {
		this.data = data;
		this.code = code;
		this.detail = detail;
		this.message = message;
	}
}
