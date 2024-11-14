package com.cyberpeace.cr.teamtrain.service.bo.situation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 团队态势任务统计学
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class TeamSituationTaskStatistics implements Serializable {

	/**
	 * 成功数
	 */
	@ApiModelProperty(value = "成功数")
	private Integer success;

	/**
	 * 失败数
	 */
	@ApiModelProperty(value = "失败数")
	private Integer fail;

	/**
	 * 待审核数
	 */
	@ApiModelProperty(value = "待审核数")
	private Integer wait;

	/**
	 * 未上报
	 */
	@ApiModelProperty(value = "未上报")
	private Integer unsubmitted;
}
