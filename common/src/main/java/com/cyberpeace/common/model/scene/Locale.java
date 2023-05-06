package com.cyberpeace.common.model.scene;

import java.io.Serializable;

import lombok.Data;

/**
 * 语言环境
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class Locale implements Serializable {

	private String LANGUAGE;

	private String COUNTRY;

	private String VARIANT;
}
