package com.cyberpeace.oj.practice.service.bo;

import java.util.List;

import com.cyberpeace.common.model.PatternBase;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

/**
 * 实操列表查询博
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PatternBase
 * @since 1.0.0
 */
@Getter
@Setter
@ApiModel
public class OperationListQueryBo extends PatternBase {

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
	 * 难度
	 *
	 * @see Integer
	 */
	private Integer difficulty;

	/**
	 * 解决
	 *
	 * @see Boolean
	 */
	private Boolean solved;

}
