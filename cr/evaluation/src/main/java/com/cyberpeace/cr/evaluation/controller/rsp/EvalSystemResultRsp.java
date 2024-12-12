package com.cyberpeace.cr.evaluation.controller.rsp;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * 评估系统结果响应
 *
 * @author SN
 * @date 2024/11/08
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
public class EvalSystemResultRsp {

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "名称")
	private String name;

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "ID")
	private String resourceId;

	/**
	 * exe 循环
	 */
	@ApiModelProperty(value = "执行轮次")
	private Integer exeCycle;

	/**
	 * 第一分
	 */
	@ApiModelProperty(value = "一级得分")
	private Integer firstScore;

	/**
	 * Second Score 第二分
	 */
	@ApiModelProperty(value = "二级得分")
	private Integer secondScore;

	/**
	 * 第三分
	 */
	@ApiModelProperty(value = "三级得分")
	private Integer thirdScore;

	/**
	 * 第四分
	 */
	@ApiModelProperty(value = "四级得分")
	private Integer fourthScore;

	/**
	 * 第五分
	 */
	@ApiModelProperty(value = "五级得分")
	private Integer fifthScore;

	/**
	 * 子级
	 */
	@ApiModelProperty(value = "子级列表")
	private List<EvalSystemResultRsp> children;

}