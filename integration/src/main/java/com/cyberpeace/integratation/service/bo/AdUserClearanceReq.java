package com.cyberpeace.integratation.service.bo;

import lombok.Data;

@Data
public class AdUserClearanceReq {

	private String user_id;

	private String auth;

	public AdUserClearanceReq(String userId) {
		this.user_id = userId;
		this.auth = "bSkjsdaklfjaDA28dD";
	}
}
