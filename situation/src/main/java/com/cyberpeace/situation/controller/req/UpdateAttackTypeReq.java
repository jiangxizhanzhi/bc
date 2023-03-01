package com.cyberpeace.situation.controller.req;

import lombok.Data;

import java.io.Serializable;

@Data
public class UpdateAttackTypeReq extends AddAttackTypeReq implements Serializable {

	private String resourceId;

}
