package com.cyberpeace.space.service.context.index;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * 编号策略
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Getter
@Setter
public class IndexStrategy {

	/**
	 * 操作系统
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "操作系统")
	private Integer os = 0;

	/**
	 * 数据库
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "数据库")
	private Integer database = 0;

	/**
	 * 中间件
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "Web中间件")
	private Integer middleware = 0;

	/**
	 * 交换机
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "交换机")
	private Integer switchboard = 0;

	/**
	 * 路由器
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "路由器")
	private Integer router = 0;

	/**
	 * 大数据
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "大数据平台")
	private Integer bigData = 0;

	/**
	 * 防病毒
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "防病毒系统")
	private Integer antiVirus = 0;

	/**
	 * 终端
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "终端安全系统")
	private Integer terminal = 0;

	/**
	 * 边境
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "边界防护设备")
	private Integer border = 0;

	/**
	 * 侵入检查
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "入侵检测系统:")
	private Integer invasionCheck = 0;

	/**
	 * 使用
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "策略库使用次数")
	private Integer use = 0;

	/**
	 * 和
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "总数")
	private Integer sum = 0;

	/**
	 * 今天添加
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "今日新增策略库")
	private Integer addedToday = 0;

	/**
	 * 今天更新
	 *
	 * @see Integer
	 */
	@ApiModelProperty(value = "今日修改策略库")
	private Integer updatedToday = 0;

	/**
	 * Cal Sum
	 *
	 * @throws
	 */
	public void calSum() {
		this.sum = this.os + this.database + this.middleware + this.switchboard + this.router
				+ this.bigData + this.antiVirus + this.terminal + this.border + this.invasionCheck;
	}
}
