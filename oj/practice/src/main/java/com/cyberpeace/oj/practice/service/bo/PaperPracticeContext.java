package com.cyberpeace.oj.practice.service.bo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 试卷质练习背景
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
@ApiModel
public class PaperPracticeContext implements Serializable {

	/**
	 * 资源id
	 */
	@ApiModelProperty("习题编号")
	@NotBlank
	private String resourceId;

	/**
	 * 裁判id
	 */
	@ApiModelProperty("快照编号")
	private String refId;

	/**
	 * 练习类型
	 */
	@ApiModelProperty("习题类型")
	@NotNull
	private Integer practiceType;

	/**
	 * 得分
	 */
	@ApiModelProperty("课后练习分值")
	@NotNull
	private Double score;

	/**
	 * 选项 random
	 */
	@ApiModelProperty("选项是否随机")
	private Boolean optionRandom;

}
