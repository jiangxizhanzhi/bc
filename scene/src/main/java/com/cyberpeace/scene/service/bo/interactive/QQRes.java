package com.cyberpeace.scene.service.bo.interactive;

import lombok.Data;

@Data
public class QQRes {

	private String number;

	private String logo;

	public QQRes(String number, String logo){
		this.number = number;
		this.logo = logo;
	}
}
