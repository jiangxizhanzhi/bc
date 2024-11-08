package com.cyberpeace.oj.practice.service.bo;

import java.util.List;

import com.cyberpeace.common.model.PatternBase;
import lombok.Data;

/**
 * lib practice query bo
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class LibPracticeQueryBo extends PatternBase {

	/**
	 * 资源id列表
	 */
	private List<String> resourceIds;

	/**
	 * 实践设置id
	 */
	private String practiceSetId;

	/**
	 * 类别id列表
	 */
	private List<String> categoryIds;

	/**
	 * 问题类型
	 */
	private Integer questionType;

}
