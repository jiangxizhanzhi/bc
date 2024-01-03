package com.cyberpeace.oj.practice.dao.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OperationExamProperty implements Serializable {

	@ApiModelProperty("考试时长 分钟")
	private Integer time;

	@ApiModelProperty("ctf题数量")
	private Integer[] ctfCount;

	@ApiModelProperty("ctf题分数")
	private Integer[] ctfScore;

	@ApiModelProperty("维护题数量")
	private Integer maintenanceCount;

	@ApiModelProperty("维护题分数")
	private Integer maintenanceScore;
}
