package com.cyberpeace.common.model.scene;

import java.io.Serializable;

/**
 * MQ 上下文
 *
 * @author SN
 * @date 2024/11/07
 */
public class MqContext implements Serializable {

	/**
	 * 用户id
	 */
	private String USER_ID;

	/**
	 * 叫链id
	 */
	private String CALL_CHAIN_ID;

	/**
	 * 现场
	 */
	private Locale LOCALE;

	/**
	 * 通过
	 */
	private String PASS_THROUGH;

	public String getPassThrough() {
		return PASS_THROUGH;
	}

	public void setPASS_THROUGH(String PASS_THROUGH) {
		this.PASS_THROUGH = PASS_THROUGH;
	}

	public String getUserId() {
		return USER_ID;
	}

	public void setUSER_ID(String USER_ID) {
		this.USER_ID = USER_ID;
	}

	public String getCallChainId() {
		return CALL_CHAIN_ID;
	}

	public void setCALL_CHAIN_ID(String CALL_CHAIN_ID) {
		this.CALL_CHAIN_ID = CALL_CHAIN_ID;
	}

	public Locale getLocale() {
		return LOCALE;
	}

	public void setLOCALE(Locale LOCALE) {
		this.LOCALE = LOCALE;
	}
}
