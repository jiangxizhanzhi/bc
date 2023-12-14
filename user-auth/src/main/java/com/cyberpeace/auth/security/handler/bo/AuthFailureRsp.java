package com.cyberpeace.auth.security.handler.bo;

import com.cyberpeace.common.model.BaseRsp;

import lombok.Data;

@Data
public class AuthFailureRsp extends BaseRsp {

	public AuthFailureRsp(String message) {
		this.setMessage(message);
	}
}
