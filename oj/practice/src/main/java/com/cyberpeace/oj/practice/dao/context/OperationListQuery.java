package com.cyberpeace.oj.practice.dao.context;

import com.cyberpeace.common.model.PatternBase;
import lombok.Data;

import java.util.List;

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
	 * 课程方向id
	 */
	private List<String> courseCategoryIds;

	/**
	 * 难度
	 */
	private Integer difficulty;

}
