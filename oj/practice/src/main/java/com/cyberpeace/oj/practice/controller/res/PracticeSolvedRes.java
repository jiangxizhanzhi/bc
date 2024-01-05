package com.cyberpeace.oj.practice.controller.res;

import java.io.Serializable;

import com.cyberpeace.oj.practice.dao.entity.PracticeSolved;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class PracticeSolvedRes implements Serializable {

	@ApiModelProperty("练习id")
	private String practiceId;

	@ApiModelProperty("答题状态")
	private Integer solved;

	@ApiModelProperty("是否标记")
	private boolean marked;

	@ApiModelProperty("是否作答")
	private boolean answered;

	private Integer type;

	public boolean answered() {
		return !this.solved.equals(PracticeSolved.SolvedStatus.NOT_START.getCode());
	}

}
