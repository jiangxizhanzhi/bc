package com.cyberpeace.evaluate.dao.entity;

import com.cyberpeace.common.model.BaseTable;

import lombok.Data;

@Data
public class TestIndex extends BaseTable {

	/**
	 * 采集结果：工具自动获取
	 */
	public static final int RESULT_FROM_TOOL = 1;

	/**
	 * 采集结果：人工录入
	 */
	public static final int RESULT_FROM_MANUAL = 2;

	private String indexId;

	private String indexName;

	private Integer resultGetType;

	private String collectToolId;

	private String itemId;
}
