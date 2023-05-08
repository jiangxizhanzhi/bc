package com.cyberpeace.bs.service.bo.statistics;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 行为仪表数据
 * 用于仪表大盘页面
 *
 * @author SN
 * @date 2023/05/08
 */
@Data
public class BehaviorMeterBo implements Serializable {

	/**
	 * 仪表节点id
	 */
	@ApiModelProperty(value = "仪表节点id")
	private String id;

	/**
	 * ip地址
	 */
	@ApiModelProperty(value = "仪表节点ip")
	private String ip;

	/**
	 * 仪表节点名称
	 */
	@ApiModelProperty(value = "仪表节点名称")
	private String name;

	/**
	 * 网卡
	 * 非必要数据
	 */
	@ApiModelProperty(value = "")
	private String nics;

	/**
	 * 密码
	 */
	@ApiModelProperty(value = "密码")
	private String password;

	/**
	 * 仪表resourceId，用于识别仪表
	 */
	@ApiModelProperty(value = "仪表resourceId，用于启动")
	private String resourceId;

	@ApiModelProperty(value = "")
	private String server;

	/**
	 * 令牌
	 */
	@ApiModelProperty(value = "令牌")
	private String token;

	/**
	 * 类型
	 * 固定为cpcloud_behavior
	 */
	@ApiModelProperty(value = "")
	private String type = "cpcloud_behavior";

	/**
	 * 用户名
	 */
	@ApiModelProperty(value = "用户名")
	private String username;

	/**
	 * 场景名称
	 */
	@ApiModelProperty(value = "试验任务名称")
	private String sceneName;

	/**
	 * 状态
	 */
	@ApiModelProperty(value = "执行状态（0：未开始，1：运行中，3：已结束）")
	private Integer status;
}
