package com.cyberpeace.common.model.scene;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 现场
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class Locale implements Serializable {

	/**
	 * 语言
	 */
	private String LANGUAGE;

	/**
	 * 国家
	 */
	private String COUNTRY;

	/**
	 * 变体
	 */
	private String VARIANT;
}
