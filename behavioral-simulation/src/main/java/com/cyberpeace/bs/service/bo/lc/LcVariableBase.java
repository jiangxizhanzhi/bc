package com.cyberpeace.bs.service.bo.lc;

import java.io.Serializable;

import lombok.Data;

@Data
public class LcVariableBase implements Serializable {

	/**
	 * 变量id
	 */
	private String dataId;

	/**
	 * 变量名称
	 */
	private String name;

	/**
	 * 变量值
	 */
	private String varValue;
}
