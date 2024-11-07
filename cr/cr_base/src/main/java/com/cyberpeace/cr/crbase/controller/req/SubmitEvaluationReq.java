package com.cyberpeace.cr.crbase.controller.req;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 提交评估请求体
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
@Slf4j
public class SubmitEvaluationReq {

	/**
	 * 房间id
	 */
	@ApiModelProperty(value = "房间ID")
	private String roomId;

	/**
	 * 提交id
	 */
	@ApiModelProperty(value = "任务提交id")
	private String submitId;

	/**
	 * 评估状态
	 */
	@ApiModelProperty(value = "评估结果")
	private Integer evaluateStatus;

	/**
	 * 原因
	 */
	@ApiModelProperty(value = "预设原因")
	private Integer reason;

	/**
	 * 详情
	 */
	@ApiModelProperty(value = "裁决详情")
	private String detail;

	/**
	 * 得分
	 */
	@ApiModelProperty(value = "分数改判")
	private Integer score;

	public void setScore(String score) {
		// 处理打分为空字符串的情况
		if (StringUtils.isBlank(score)) {
			this.score = 0;
			return;
		}
		// 校验打分为非零整数
		try {
			this.score = new Integer(score);
			if (this.score < 0) {
				log.error("score = [{}]", score);
				throw new RuntimeException();
			}
		} catch (Exception e) {
			log.error("Invalid score, supposed to be non-negative integer! score = [{}]", score);
			throw new BusinessException(CrBusinessErrorCode.INVALID_SCORE);
		}
	}
}
