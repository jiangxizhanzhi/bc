package com.cyberpeace.cr.crbase.service.context.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 中台事件
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class MpEvent implements Serializable {

	/**
	 * 操作类型
	 */
	@ApiModelProperty("动作类型：attack:攻击,defense:防御")
	private String actionType;

	/**
	 * 查看attack名字
	 */
	@ApiModelProperty("攻防类型名称")
	private List<String> attCkNames;

	/**
	 * {@linkplain com.cyberpeace.situation.constant.SituationGuideScheme.AttackUrlEnum mpCode}
	 * {@linkplain com.cyberpeace.situation.constant.SituationGuideScheme.DefenseUrlEnum mpCode}
	 */
	@ApiModelProperty("攻防类型")
	private List<String> attCks;

	/**
	 * 事件名称
	 */
	@ApiModelProperty("事件名称")
	private String eventName;

	/**
	 * id
	 */
	@ApiModelProperty("场景id")
	private String id;

	/**
	 * 源节点id
	 */
	@ApiModelProperty("源节点id")
	private String sourceNodeId;

	/**
	 * 源节点ip地址
	 */
	@ApiModelProperty("源节点ip")
	private String sourceNodeIp;

	/**
	 * 源节点名称
	 */
	@ApiModelProperty("源节点名称")
	private String sourceNodeName;

	/**
	 * 源节点端口
	 */
	@ApiModelProperty("源节点端口")
	private String sourceNodePort;

	/**
	 * 目标节点id
	 */
	@ApiModelProperty("目的点id")
	private String targetNodeId;

	/**
	 * 目标节点ip地址
	 */
	@ApiModelProperty("目的点ip")
	private String targetNodeIp;

	/**
	 * 目标节点名称
	 */
	@ApiModelProperty("目的点名称")
	private String targetNodeName;

	/**
	 * 目标节点端口
	 */
	@ApiModelProperty("目的点端口")
	private String targetNodePort;

	/**
	 * 触发器类型
	 */
	@ApiModelProperty("触发类型:host:主机采集,flow:流量采集")
	private String triggerType;

	/**
	 * 时间
	 */
	@ApiModelProperty("时间")
	private String time;

}
