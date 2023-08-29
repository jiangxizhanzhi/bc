package com.cyberpeace.cr.teamtrain.service.bo.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class StudentScoreVo implements Serializable {

	@ApiModelProperty(value = "学员id")
	private String resourceId;

	@ApiModelProperty(value = "姓名")
	private String name;

	@ApiModelProperty(value = "头像")
	private String avatar;

	@ApiModelProperty(value = "分数")
	private Integer score = 0;

	public void addScore(Integer addend) {
		this.score += addend;
	}
}
