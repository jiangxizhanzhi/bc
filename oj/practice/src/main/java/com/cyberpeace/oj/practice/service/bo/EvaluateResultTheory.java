package com.cyberpeace.oj.practice.service.bo;


import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EvaluateResultTheory implements Serializable {

	@ApiModelProperty("通过状态")
	private Integer status;

	@ApiModelProperty("得分")
	private Double score;

	@ApiModelProperty("总分")
	private Double totalScore;

	@ApiModelProperty("资源编号")
	private String resourceId;

	@ApiModelProperty("正确答案")
	private String answer;
}
