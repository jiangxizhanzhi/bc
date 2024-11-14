package com.cyberpeace.oj.practice.service.bo;

import com.cyberpeace.common.model.PatternBase;
import lombok.Data;

import java.util.List;

/**
 * 理论列表查询Bo
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class TheoryListQueryBo extends PatternBase {

	/**
	 * 练习设置id
	 */
	private String practiceSetId;

	/**
	 * 类别id列表
	 */
	private List<String> categoryIds;

	/**
	 * 解决
	 */
	private Integer solved;

	/**
	 * 难度
	 */
	private Integer difficulty;

	/**
	 * 理论类型
	 */
	private Integer theoryType;

}
