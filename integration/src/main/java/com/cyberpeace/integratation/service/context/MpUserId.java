package com.cyberpeace.integratation.service.context;

import java.io.Serializable;

import lombok.Data;

@Data
public class MpUserId implements Serializable {

	/**
	 * 用户id
	 */
	private String user_id;

	public MpUserId(String userId) {
		this.user_id = userId;
	}

}
