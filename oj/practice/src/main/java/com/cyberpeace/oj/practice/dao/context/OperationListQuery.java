package com.cyberpeace.oj.practice.dao.context;

import com.cyberpeace.common.model.PatternBase;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 实操列表查询
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PatternBase
 * @since 1.0.0
 */
@Getter
@Setter
public class OperationListQuery extends PatternBase {

	/**
	 * 资源id列表
	 *
	 * @see List<String>
	 */
	private List<String> resourceIds;

	/**
	 * 实践设置id列表
	 *
	 * @see List<String>
	 */
	private List<String> practiceSetIds;

	/**
	 * 类别id列表
	 *
	 * @see List<String>
	 */
	private List<String> categoryIds;

	/**
	 * 课程方向id
	 *
	 * @see List<String>
	 */
	private List<String> courseCategoryIds;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	private Integer difficulty;

	/**
	 * flag类型
	 *
	 * @see Integer
	 */
	private Integer dynamicFlag;

}
