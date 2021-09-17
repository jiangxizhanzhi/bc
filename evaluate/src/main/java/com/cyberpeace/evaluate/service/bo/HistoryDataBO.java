package com.cyberpeace.evaluate.service.bo;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Data;

@Data
public class HistoryDataBO {

	/**指标指标名称 */
	private String indexName;

	/**测试项名称 */
	private String itemName;

	/**测试项id */
	private String itemId;

	private String indexId;

	/**测评的数据 按顺序 第一次测试 ，第一个数值 */
	private List<String> values = Lists.newArrayList();

}
