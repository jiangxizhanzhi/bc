package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PaperRecordSummaryRes implements Serializable {

	@ApiModelProperty("组卷时间")
	private String paperTime;

	@ApiModelProperty("题目数量")
	private Integer count;

	@ApiModelProperty("是否已完成")
	private Boolean isFinish;

	@ApiModelProperty("答对数量")
	private Integer right;

	@ApiModelProperty("答错数量")
	private Integer wrong;

	@ApiModelProperty("未答数量")
	private Integer notStart;

	@ApiModelProperty("正确率")
	private Integer accuracyRate;
}
