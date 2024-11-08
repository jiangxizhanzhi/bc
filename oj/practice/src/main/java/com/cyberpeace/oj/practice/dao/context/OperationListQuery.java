package com.cyberpeace.oj.practice.dao.context;

import java.util.List;

import com.cyberpeace.common.model.PatternBase;

import lombok.Data;

/**
 * 实操列表查询
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class OperationListQuery extends PatternBase {

	/**
	 * 资源id列表
	 */
	private List<String> resourceIds;

	/**
	 * 实践设置id列表
	 */
	private List<String> practiceSetIds;

	/**
	 * 类别id列表
	 */
	private List<String> categoryIds;

	/**
	 * 困难
	 */
	private Integer difficulty;

}
