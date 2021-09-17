package com.cyberpeace.evaluate.dao.entity;

import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
public class TestSubActionOutput extends BaseTable {

	private String itemId;

	private String actionId;

	private String instanceId;

	private String output;
}
