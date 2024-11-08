package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 纸记录结果
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class PaperRecordRes implements Serializable {

	/**
	 * Paper Time （造纸时间）
	 */
	@ApiModelProperty("组卷时间")
	private String paperTime;

	/**
	 * 结束了
	 */
	@ApiModelProperty("试卷是否已完成")
	private Boolean isFinish;

	/**
	 * 纸id
	 */
	@ApiModelProperty("组卷id")
	private String paperId;
}
