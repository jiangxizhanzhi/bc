package com.cyberpeace.evaluate.dao.entity;

import com.cyberpeace.common.model.BaseTable;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class TestItemInput extends BaseTable {

	private String itemId;

	private String instanceId;

	private String actionInput;

	private Integer status;

	/**
	 * 动作对应的输出。
	 * 作为指标项的输入
	 */
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String actionResult;
}
