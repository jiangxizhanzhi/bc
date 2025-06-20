package com.cyberpeace.oj.practice.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 纸记录总结结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see Serializable
 * @since 1.0.0
 */
@Getter
@Setter
public class PaperRecordSummaryRes implements Serializable {
		
	// 明确指定版本UID
	private static final long serialVersionUID = 1L;
	/**
	 * 纸id
	 *
	 * @see String
	 */
	@ApiModelProperty("组卷id")
	private String paperId;

	/**
	 * Paper Time （造纸时间）
	 *
	 * @see String
	 */
	@ApiModelProperty("组卷时间")
	private String paperTime;

	/**
	 * 计数
	 *
	 * @see Integer
	 */
	@ApiModelProperty("题目数量")
	private Integer count;

	/**
	 * 结束了
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否已完成")
	private Boolean isFinish;

	/**
	 * 右
	 *
	 * @see Integer
	 */
	@ApiModelProperty("答对数量")
	private Integer right;

	/**
	 * 错
	 *
	 * @see Integer
	 */
	@ApiModelProperty("答错数量")
	private Integer wrong;

	/**
	 * 未开始
	 *
	 * @see Integer
	 */
	@ApiModelProperty("未答数量")
	private Integer notStart;

	/**
	 * 准确率
	 *
	 * @see Integer
	 */
	@ApiModelProperty("正确率")
	private Integer accuracyRate;
}
