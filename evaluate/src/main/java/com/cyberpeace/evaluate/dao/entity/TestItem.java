package com.cyberpeace.evaluate.dao.entity;

import com.cyberpeace.common.model.BaseTable;
import lombok.Data;

@Data
public class TestItem extends BaseTable {

	/**
	 * 工具自动运行
	 */
	public static final int AUTO_RUN = 1;

	/**
	 * 工具手动运行
	 */
	public static final int AUTO_MANUAL = 0;

	private String itemId;

	private String itemName;

	private String operation;

	private String expectation;

	private Integer autoRun;

	private String toolId;

	private String actionId;

	private String schemeId;

	private String logoUrl;
}
