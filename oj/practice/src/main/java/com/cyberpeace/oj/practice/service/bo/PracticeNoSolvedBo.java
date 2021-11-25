package com.cyberpeace.oj.practice.service.bo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel
public class PracticeNoSolvedBo implements Serializable {

	@ApiModelProperty("是否解题成功")
	private Boolean isSolved;

}
