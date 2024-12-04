package com.cyberpeace.oj.practice.controller.req.external;

import com.cyberpeace.common.model.PatternBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * 实践随机选择请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see PatternBase
 * @since 1.0.0
 */
@Data
public class PracticeRandomChoiceReq extends PatternBase {

	/**
	 * 实践设置
	 *
	 * @see String
	 */
	@ApiModelProperty("习题集查询")
	private String practiceSet;

	/**
	 * 类别
	 *
	 * @see String
	 */
	@ApiModelProperty("分类查询")
	private String category;

	/**
	 * 问题类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "理论基础题型查询: 0-单选, 1-多选, 2-判断, 3-填空, 4-简答",
			allowableValues = "0, 1, 2, 3, 4")
	private Integer questionType;

	/**
	 * 难度
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "难度查询", allowableValues = "0, 1, 2")
	private Integer difficulty;

	/**
	 * 计数
	 *
	 * @see Integer
	 */
	@NotNull
	@ApiModelProperty(value = "获取数量", required = true)
	private Integer count;

	/**
	 * 得分
	 *
	 * @see Double
	 */
	@NotNull
	@ApiModelProperty("设置默认分值")
	private Double score;

	/**
	 * 用户id
	 *
	 * @see String
	 */
	@ApiModelProperty("用户编号")
	private String userId;
}
