package com.cyberpeace.evaluate.dao.entity;

import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
public class TestItemLink extends BaseTable {

	private String schemeId;

	private String itemId;

	private Integer idx;
}
