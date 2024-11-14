package com.cyberpeace.oj.practice.dao.context;

import lombok.Data;

/**
 * 理论列表查询
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class TheoryListQuery extends OperationListQuery {

	/**
	 * 问题类型
	 */
	private Integer questionType;

}
