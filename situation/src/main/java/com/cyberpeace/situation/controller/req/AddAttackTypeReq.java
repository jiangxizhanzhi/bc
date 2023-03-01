package com.cyberpeace.situation.controller.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class AddAttackTypeReq implements Serializable {

	private Integer actionType;

	private String cnDesc;

	private String enDesc;

	private Boolean isParalysis;

	private Boolean isExploit;

	private Boolean isVirus;

}
