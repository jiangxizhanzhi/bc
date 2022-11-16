package com.cyberpeace.bs.utils;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class LcVariableModifyReq implements Serializable {

	private String environmentId;

	private String id;

	private String name;

	private String projectId;

	private Date updateTime;

	private String value;

	private String variableType = "Text";
}
