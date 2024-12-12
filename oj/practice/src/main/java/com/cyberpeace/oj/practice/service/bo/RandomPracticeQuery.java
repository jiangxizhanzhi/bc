package com.cyberpeace.oj.practice.service.bo;

import java.util.List;

import com.cyberpeace.common.model.PatternBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 随机练习查询
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PatternBase
 * @since 1.0.0
 */
@Getter
@Setter
public class RandomPracticeQuery extends PatternBase {

	/**
	 * 实践设置id
	 *
	 * @see String
	 */
	@ApiModelProperty("习题集编号")
	private String practiceSetId;

	/**
	 * 类别id列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("分类编号")
	private List<String> categoryIds;

	/**
	 * 问题类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("题型 0=单选 1-多选.....")
	private Integer questionType;

	/**
	 * 计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("习题数量")
	private Integer count;

	/**
	 * 查询id列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("已选择的习题集编号")
	private List<String> selectIds;
}
