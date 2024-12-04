package com.cyberpeace.oj.practice.dao.context;

import lombok.Data;

/**
 * 理论列表查询
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see OperationListQuery
 * @since 1.0.0
 */
@Data
public class TheoryListQuery extends OperationListQuery {

	/**
	 * 问题类型
	 *
	 * @see Integer
	 */
	private Integer questionType;

}
