package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 纸记录总结结果由日
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class PaperRecordSummaryResByDay implements Serializable {

	/**
	 * 时间
	 *
	 * @see String
	 */
	@ApiModelProperty("时间")
	private String time;

	/**
	 * 总结结果
	 *
	 * @see List<PaperRecordSummaryRes>
	 */
	@ApiModelProperty("组卷记录列表")
	private List<PaperRecordSummaryRes> summaryRes;

}
