package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 试卷记录结果
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class PaperRecordRes implements Serializable {

	/**
	 * Paper Time （造试卷时间）
	 */
	@ApiModelProperty("组卷时间")
	private String paperTime;

	/**
	 * 结束了
	 */
	@ApiModelProperty("试卷是否已完成")
	private Boolean isFinish;

	/**
	 * 试卷id
	 */
	@ApiModelProperty("组卷id")
	private String paperId;
}
