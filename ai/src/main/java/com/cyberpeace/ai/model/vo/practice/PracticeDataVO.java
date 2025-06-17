package com.cyberpeace.ai.model.vo.practice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("实操题各方向题目练习情况")
public class PracticeDataVO {

	@ApiModelProperty("题目方向")
	private String direction;

	@ApiModelProperty("题目数量")
	private Integer count;

	@ApiModelProperty("解出数量")
	private Integer solved;

}