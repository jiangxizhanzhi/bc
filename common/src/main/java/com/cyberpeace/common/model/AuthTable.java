package com.cyberpeace.common.model;

import lombok.Data;

@Data
public class AuthTable extends BaseTable {

	private Integer operationAuthType;

	private String operationAuthIds;

	private Integer readAuthType;

	private String readAuthIds;
}
