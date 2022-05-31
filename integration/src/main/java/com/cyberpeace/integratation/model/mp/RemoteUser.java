package com.cyberpeace.integratation.model.mp;

import lombok.Data;

import java.io.Serializable;

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

}
