package com.cyberpeace.oj.ojbase.service.bo.check;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class CheckPointQuestion implements Serializable {

	@ApiModelProperty(value = "id")
	private String resourceId;

	@ApiModelProperty(value = "")
	private String blankCount = null;

	@ApiModelProperty(value = "名称")
	private String title;

	@ApiModelProperty(value = "描述")
	private String content;

	@ApiModelProperty(value = "方向id")
	private String categoryId;

	@ApiModelProperty(value = "方向")
	private String categoryName;

	/**
	 * 只有理论题
	 */
	@ApiModelProperty(value = "理论题")
	private Integer practiceType = 0;

	@ApiModelProperty(value = "类型")
	private String questionType;

	@ApiModelProperty(value = "选项")
	private String option;

	@ApiModelProperty(value = "分数")
	private Integer score;

	@ApiModelProperty(value = "答案")
	private String answer;
}
