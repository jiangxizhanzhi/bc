package com.cyberpeace.space.service.context.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 编号统治
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class IndexRule {

	/**
	 * 基础
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "基础数据规则")
	private Integer base = 0;

	/**
	 * 警告
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "告警字典规则")
	private Integer warning = 0;

	/**
	 * 威胁
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "威胁防御规则")
	private Integer threat = 0;

	/**
	 * 比赛
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "比赛及训练评估规则")
	private Integer race = 0;

	/**
	 * 和
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "总数")
	private Integer sum = 0;

	/**
	 * 使用
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "使用次数")
	private Integer use = 0;

	/**
	 * Cal Sum
	 *
	 * @throws
	 */
	public void calSum() {
		this.sum = this.base + this.warning + this.race + this.threat;
	}
}
