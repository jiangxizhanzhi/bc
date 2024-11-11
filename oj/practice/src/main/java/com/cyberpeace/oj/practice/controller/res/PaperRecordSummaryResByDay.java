package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 试卷记录总结结果由日
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class PaperRecordSummaryResByDay implements Serializable {

	/**
	 * 时间
	 */
	@ApiModelProperty("时间")
	private String time;

	/**
	 * 总结结果
	 */
	@ApiModelProperty("组卷记录列表")
	private List<PaperRecordSummaryRes> summaryRes;

}
