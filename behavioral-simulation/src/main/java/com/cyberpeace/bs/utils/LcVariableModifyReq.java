package com.cyberpeace.bs.utils;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class LcVariableModifyReq implements Serializable {

	private String environmentId = "1580451559829852161";

	private String id;

	private String name;

	private String projectId = "1580451559792103425";

	private Date updateTime;

	private String value;

	private String variableType = "Text";
}
