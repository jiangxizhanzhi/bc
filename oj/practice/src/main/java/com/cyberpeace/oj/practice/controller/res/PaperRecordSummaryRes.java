package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 纸记录总结结果
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class PaperRecordSummaryRes implements Serializable {

	/**
	 * 纸id
	 */
	@ApiModelProperty("组卷id")
	private String paperId;

	/**
	 * Paper Time （造纸时间）
	 */
	@ApiModelProperty("组卷时间")
	private String paperTime;

	/**
	 * 计数
	 */
	@ApiModelProperty("题目数量")
	private Integer count;

	/**
	 * 结束了
	 */
	@ApiModelProperty("是否已完成")
	private Boolean isFinish;

	/**
	 * 右
	 */
	@ApiModelProperty("答对数量")
	private Integer right;

	/**
	 * 错
	 */
	@ApiModelProperty("答错数量")
	private Integer wrong;

	/**
	 * 未开始
	 */
	@ApiModelProperty("未答数量")
	private Integer notStart;

	/**
	 * 准确率
	 */
	@ApiModelProperty("正确率")
	private Integer accuracyRate;
}
