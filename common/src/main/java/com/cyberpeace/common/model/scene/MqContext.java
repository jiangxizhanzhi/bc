package com.cyberpeace.common.model.scene;

import java.io.Serializable;

/**
 * MQ 上下文
 *
 * @author SN
 * @date 2024/11/08
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

	/**
	 * 获取通过通过
	 *
	 * @return {@code String }
	 */
	public String getPassThrough() {
		return PASS_THROUGH;
	}

	/**
	 * 设置通过通过
	 *
	 * @param PASS_THROUGH 通过
	 */
	public void setPASS_THROUGH(String PASS_THROUGH) {
		this.PASS_THROUGH = PASS_THROUGH;
	}

	/**
	 * 获取用户id
	 *
	 * @return {@code String }
	 */
	public String getUserId() {
		return USER_ID;
	}

	/**
	 * 设置用户id
	 *
	 * @param USER_ID 用户id
	 */
	public void setUSER_ID(String USER_ID) {
		this.USER_ID = USER_ID;
	}

	/**
	 * 获取叫链id
	 *
	 * @return {@code String }
	 */
	public String getCallChainId() {
		return CALL_CHAIN_ID;
	}

	/**
	 * 设置叫链id
	 *
	 * @param CALL_CHAIN_ID 叫链id
	 */
	public void setCALL_CHAIN_ID(String CALL_CHAIN_ID) {
		this.CALL_CHAIN_ID = CALL_CHAIN_ID;
	}

	/**
	 * 获取现场
	 *
	 * @return {@code Locale }
	 */
	public Locale getLocale() {
		return LOCALE;
	}

	/**
	 * 设置现场
	 *
	 * @param LOCALE 现场
	 */
	public void setLOCALE(Locale LOCALE) {
		this.LOCALE = LOCALE;
	}
}
