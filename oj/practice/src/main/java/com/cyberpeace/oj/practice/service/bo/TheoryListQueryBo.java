package com.cyberpeace.oj.practice.service.bo;

import com.cyberpeace.common.model.PatternBase;
import lombok.Data;

import java.util.List;

/**
 * 理论列表查询博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PatternBase
 * @since 1.0.0
 */
@Data
public class TheoryListQueryBo extends PatternBase {

	/**
	 * 实践设置id
	 *
	 * @see String
	 */
	private String practiceSetId;

	/**
	 * 类别id列表
	 *
	 * @see List<String>
	 */
	private List<String> categoryIds;

	/**
	 * 解决
	 *
	 * @see Integer
	 */
	private Integer solved;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	private Integer difficulty;

	/**
	 * 理论类型
	 *
	 * @see Integer
	 */
	private Integer theoryType;

	/**
	 * 课程类型
	 *
	 * @see String
	 */
	private String courseCategoryId;

}
