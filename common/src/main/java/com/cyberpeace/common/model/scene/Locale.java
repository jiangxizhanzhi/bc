package com.cyberpeace.common.model.scene;

import lombok.Data;

import java.io.Serializable;

@Data
public class Locale implements Serializable {

	private String LANGUAGE;

	private String COUNTRY;

	private String VARIANT;
}
