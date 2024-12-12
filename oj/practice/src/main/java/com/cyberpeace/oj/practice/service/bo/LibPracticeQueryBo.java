package com.cyberpeace.oj.practice.service.bo;

import java.util.List;

import com.cyberpeace.common.model.PatternBase;
import lombok.Getter;
import lombok.Setter;

/**
 * lib practice query bo
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PatternBase
 * @since 1.0.0
 */
@Getter
@Setter
public class LibPracticeQueryBo extends PatternBase {

	/**
	 * 资源id列表
	 *
	 * @see List<String>
	 */
	private List<String> resourceIds;

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
	 * 问题类型
	 *
	 * @see Integer
	 */
	private Integer questionType;

}
