package com.cyberpeace.integratation.model.mp;

import java.io.Serializable;

import lombok.Data;

@Data
public class GxToken implements Serializable {

	private String token;

	public GxToken(String token) {
		this.token = token;
	}

	public GxToken() {}

}
