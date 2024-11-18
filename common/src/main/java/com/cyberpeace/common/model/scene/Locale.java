package com.cyberpeace.common.model.scene;

import lombok.Data;

import java.io.Serializable;

/**
 * 现场
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class Locale implements Serializable {

	/**
	 * 语言
	 *
	 * @see String
	 */
	private String LANGUAGE;

	/**
	 * 国家
	 *
	 * @see String
	 */
	private String COUNTRY;

	/**
	 * 变体
	 *
	 * @see String
	 */
	private String VARIANT;
}
