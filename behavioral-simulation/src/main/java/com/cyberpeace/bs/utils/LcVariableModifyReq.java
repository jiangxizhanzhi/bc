package com.cyberpeace.bs.utils;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class LcVariableModifyReq implements Serializable {

	private String environmentId = "1579380252501528578";

	private String id;

	private String name;

	private String projectId = "1579380252455391234";

	private Date updateTime;

	private String value;

	private String variableType = "Text";
}
