package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;

import lombok.Data;

@Data
public class RemoteUser implements Serializable {

	/**
	 * 远程用户id
	 */
	private String userId;

	/**
	 * 远程用户token
	 */
	private String thirdToken;

	private String thirdUserId;

	private String userName;

	private Integer isAdmin;

}
