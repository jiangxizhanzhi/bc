package com.cyberpeace.oj.practice.controller.req.external;

import javax.validation.constraints.NotNull;

import com.cyberpeace.common.model.PatternBase;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PracticeRandomChoiceReq extends PatternBase {

	@ApiModelProperty("习题集查询")
	private String practiceSet;

	@ApiModelProperty("分类查询")
	private String category;

	@ApiModelProperty(value = "理论基础题型查询: 0-单选, 1-多选, 2-判断, 3-填空, 4-简答",
			allowableValues = "0, 1, 2, 3, 4")
	private Integer questionType;

	@ApiModelProperty(value = "难度查询", allowableValues = "0, 1, 2")
	private Integer difficulty;

	@NotNull
	@ApiModelProperty("获取数量")
	private Integer count;

	@NotNull
	@ApiModelProperty("设置默认分值")
	private Double score;

	@ApiModelProperty("用户编号")
	private String userId;
}
