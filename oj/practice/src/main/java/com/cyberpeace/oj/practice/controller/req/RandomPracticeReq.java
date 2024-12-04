package com.cyberpeace.oj.practice.controller.req;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cyberpeace.common.model.PatternBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 随机实践请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PatternBase
 * @since 1.0.0
 */
@Data
@ApiModel
public class RandomPracticeReq extends PatternBase {

	/**
	 * 实践设置id
	 *
	 * @see String
	 */
	@ApiModelProperty("习题集编号")
	@NotBlank
	private String practiceSetId;

	/**
	 * 类别id
	 *
	 * @see String
	 */
	@ApiModelProperty("分类编号")
	private String categoryId;

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
	@NotNull
	private Integer count;

	/**
	 * 得分
	 *
	 * @see Double
	 */
	@ApiModelProperty("习题分值")
	@NotNull
	private Double score;

	/**
	 * 资源id列表
	 *
	 * @see List<String>
	 */
	@ApiModelProperty("已选择的习题编号")
	private List<String> resourceIds;

}
