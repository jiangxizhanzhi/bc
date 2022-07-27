package com.cyberpeace.integratation.service.context;

import java.io.Serializable;

import lombok.Data;

@Data
public class RemoteUserInfo implements Serializable {

	/**
	 * 用户id
	 */
	private String third_user_id;

	private String third_user_name;

	public RemoteUserInfo(String thirdUserId, String thirdUserName) {
		this.third_user_id = thirdUserId;
		this.third_user_name = thirdUserName;
	}

}
