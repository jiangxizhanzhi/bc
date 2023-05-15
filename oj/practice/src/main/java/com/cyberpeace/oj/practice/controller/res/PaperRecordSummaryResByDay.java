package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PaperRecordSummaryResByDay implements Serializable {

	@ApiModelProperty("时间")
	private String time;

	@ApiModelProperty("组卷记录列表")
	private List<PaperRecordSummaryRes> summaryRes;

}
