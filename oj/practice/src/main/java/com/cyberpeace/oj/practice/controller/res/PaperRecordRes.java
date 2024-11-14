package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 试卷记录结果
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class PaperRecordRes implements Serializable {

	/**
	 * 组卷时间
	 */
	@ApiModelProperty("组卷时间")
	private String paperTime;

	/**
	 * 试卷是否已完成
	 */
	@ApiModelProperty("试卷是否已完成")
	private Boolean isFinish;

	/**
	 * 组卷id
	 */
	@ApiModelProperty("组卷id")
	private String paperId;
}
