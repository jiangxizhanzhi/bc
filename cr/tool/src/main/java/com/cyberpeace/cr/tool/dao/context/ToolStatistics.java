package com.cyberpeace.cr.tool.dao.context;

import lombok.Data;

import java.io.Serializable;

/**
 * 工具统计
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class
ToolStatistics implements Serializable {

	/**
	 * 类型
	 */
	private String type;

	/**
	 * 日期
	 */
	private String date;
}
