package com.cyberpeace.cr.crbase.controller.rsp.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 导调历史响应
 *
 * @author SN
 * @date 2024/11/12
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GuideHistoryRsp implements Serializable {

	/**
	 * 事件名称
	 */
	@ApiModelProperty("事件名称")
	private String eventName;

	/**
	 * 触发器类型
	 */
	@ApiModelProperty("触发类型")
	private String triggerType;

	/**
	 * 触发代号
	 */
	@ApiModelProperty("触发类型code")
	private Integer triggerCode;

	/**
	 * 操作类型
	 */
	@ApiModelProperty("动作类型")
	private String actionType;

	/**
	 * 行动代号
	 */
	@ApiModelProperty("动作类型code")
	private Integer actionCode;

	/**
	 * 类别
	 */
	@ApiModelProperty("攻防类型")
	private List<String> category;

	/**
	 * {@linkplain com.cyberpeace.situation.constant.SituationGuideScheme.AttackUrlEnum code}
	 * {@linkplain com.cyberpeace.situation.constant.SituationGuideScheme.DefenseUrlEnum code}
	 */
	@ApiModelProperty("攻防类型code")
	private List<Integer> categoryCode;

	/**
	 * 源名称
	 */
	@ApiModelProperty("源节点名称")
	private String sourceName;

	/**
	 * 源ip地址
	 */
	@ApiModelProperty("源节点IP")
	private String sourceIp;

	/**
	 * 源端口
	 */
	@ApiModelProperty("源节点端口")
	private String sourcePort;

	/**
	 * 源id
	 */
	@ApiModelProperty("源节点id")
	private String sourceId;

	/**
	 * 目标名称
	 */
	@ApiModelProperty("目的节点名称")
	private String targetName;

	/**
	 * 目标ip地址
	 */
	@ApiModelProperty("目的节点IP")
	private String targetIp;

	/**
	 * 目标端口
	 */
	@ApiModelProperty("目的节点端口")
	private String targetPort;

	/**
	 * 目标id
	 */
	@ApiModelProperty("目的节点id")
	private String targetId;

	/**
	 * 结果
	 */
	@ApiModelProperty("结果")
	private Integer result;

	/**
	 * 岗位
	 */
	@ApiModelProperty("归属角色")
	private Integer role;

	/**
	 * 创建时间
	 */
	@ApiModelProperty("时间")
	private Date createTime;

	public GuideHistoryRsp(String eventName, String triggerType, String actionType, List<String> category) {
		this.eventName = eventName;
		this.triggerType = triggerType;
		this.actionType = actionType;
		this.category = category;
	}
}
