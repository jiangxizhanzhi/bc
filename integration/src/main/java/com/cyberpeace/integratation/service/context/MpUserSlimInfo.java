package com.cyberpeace.integratation.service.context;

import java.io.Serializable;

import lombok.Data;

@Data
public class MpUserSlimInfo implements Serializable {

	private String id;

	/**
	 * 第三方用户id
	 */
	private String thirdUserId;

	/**
	 * 第三方用户token
	 */
	private String thirdToken;

}
