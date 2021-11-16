package com.cyberpeace.oj.course.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class PracticeSolvedBo implements Serializable {

	@ApiModelProperty("用户答案")
	private String userAnswer;

	@ApiModelProperty("是否回答正确")
	private Boolean solved;

	@ApiModelProperty("资源编号")
	private String resourceId;

	@ApiModelProperty("得分")
	private Double score;

}
