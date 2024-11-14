package com.cyberpeace.cr.teamtrain.service.bo.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 态势任务进展
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class SituationTaskProgress implements Serializable {

	/**
	 * 总任务数
	 */
	@ApiModelProperty(value = "总任务数")
	private Integer total;

	/**
	 * 已下发
	 */
	@ApiModelProperty(value = "已下发")
	private Integer released;

	/**
	 * 未下发
	 */
	@ApiModelProperty(value = "未下发")
    private Integer unreleased;
}
