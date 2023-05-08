package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class PaperRecordRes implements Serializable {

	@ApiModelProperty("组卷时间")
	private String paperTime;

	@ApiModelProperty("试卷是否已完成")
	private Boolean isFinish;

	@ApiModelProperty("组卷id")
	private String paperId;
}
