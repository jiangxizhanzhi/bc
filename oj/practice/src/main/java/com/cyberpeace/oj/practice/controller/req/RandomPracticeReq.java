package com.cyberpeace.oj.practice.controller.req;

import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.cyberpeace.common.model.PatternBase;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 随机练习请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class RandomPracticeReq extends PatternBase {

	/**
	 * 练习设置id
	 */
	@ApiModelProperty("习题集编号")
	@NotBlank
	private String practiceSetId;

	/**
	 * 类别id
	 */
	@ApiModelProperty("分类编号")
	private String categoryId;

	/**
	 * 问题类型
	 */
	@ApiModelProperty("题型 0=单选 1-多选.....")
	private Integer questionType;

	/**
	 * 计数
	 */
	@ApiModelProperty("习题数量")
	@NotNull
	private Integer count;

	/**
	 * 得分
	 */
	@ApiModelProperty("习题分值")
	@NotNull
	private Double score;

	/**
	 * 资源id列表
	 */
	@ApiModelProperty("已选择的习题编号")
	private List<String> resourceIds;

}
