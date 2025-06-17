package com.cyberpeace.ai.model.vo.practice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("理论题各方向题目练习情况")
public class PracticeTheoryDataVO {

	@ApiModelProperty("题目方向")
	private String direction;

	@ApiModelProperty("题目数量")
	private Integer count;

	@ApiModelProperty("练习数量")
	private Integer practiceCount;

	@ApiModelProperty("错题数量")
	private Integer errorCount;

}