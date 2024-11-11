package com.cyberpeace.oj.practice.controller.req.external;

import javax.validation.constraints.NotNull;

import com.cyberpeace.common.model.PatternBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 练习随机选择请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class PracticeRandomChoiceReq extends PatternBase {

	/**
	 * 练习设置
	 */
	@ApiModelProperty("习题集查询")
	private String practiceSet;

	/**
	 * 类别
	 */
	@ApiModelProperty("分类查询")
	private String category;

	/**
	 * 问题类型
	 */
	@ApiModelProperty(value = "理论基础题型查询: 0-单选, 1-多选, 2-判断, 3-填空, 4-简答",
			allowableValues = "0, 1, 2, 3, 4")
	private Integer questionType;

	/**
	 * 困难
	 */
	@ApiModelProperty(value = "难度查询", allowableValues = "0, 1, 2")
	private Integer difficulty;

	/**
	 * 计数
	 */
	@NotNull
	@ApiModelProperty("获取数量")
	private Integer count;

	/**
	 * 得分
	 */
	@NotNull
	@ApiModelProperty("设置默认分值")
	private Double score;

	/**
	 * 用户id
	 */
	@ApiModelProperty("用户编号")
	private String userId;
}
