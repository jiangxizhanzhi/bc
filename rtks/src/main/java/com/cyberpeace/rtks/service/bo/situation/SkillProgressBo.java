package com.cyberpeace.rtks.service.bo.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class SkillProgressBo implements Serializable {

	@ApiModelProperty(value = "总数")
	private Integer total;

	@ApiModelProperty(value = "已完成")
	private Integer finished;

	@ApiModelProperty(value = "未完成")
	private Integer notFinished;
}
