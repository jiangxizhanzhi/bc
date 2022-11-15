package com.cyberpeace.bs.service.bo.behavior;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class LcVariableRspBo implements Serializable {

	private String name;

	private String value;

	private String variableType;

	private Date updateTime;

	private String id;
}
