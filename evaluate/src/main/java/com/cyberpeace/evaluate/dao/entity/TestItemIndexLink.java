package com.cyberpeace.evaluate.dao.entity;

import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
public class TestItemIndexLink extends BaseTable {

	private String itemId;

	private String indexId;

	private Integer idx;
}
