package com.cyberpeace.cr.report_score.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

@ApiModel("任务统计信息")
@Data
public class SubjectCompleteRep implements Serializable {

	@ApiModelProperty("队伍ID")
	private String userId;

	@ApiModelProperty("队伍名称")
	private String userName;

	@ApiModelProperty("队伍个方向统计，按顺序对应 1~5")
	private List<String> subjectScore = Arrays.asList("0", "0", "0", "0", "0");
}
