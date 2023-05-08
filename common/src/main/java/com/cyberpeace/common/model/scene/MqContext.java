package com.cyberpeace.common.model.scene;

import java.io.Serializable;

/**
 * mq上下文
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public class MqContext implements Serializable {

	private String USER_ID;

	private String CALL_CHAIN_ID;

	private Locale LOCALE;

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
