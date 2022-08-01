package com.cyberpeace.rtks.service.bo.situation;

import java.io.Serializable;

import lombok.Data;

@Data
public class MultiRoleContext implements Serializable {

	private String userId;

	private Integer type;

	private Integer count;
}
