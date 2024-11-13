package com.cyberpeace.cr.crbase.controller.req.guide;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 执行导调历史请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class ExecGuideHisReq implements Serializable {

	/**
	 * 房间id
	 */
	@ApiModelProperty("房间id")
	private String roomId;

	/**
	 * 业务类型
	 */
	@ApiModelProperty("训练类型")
	private Integer businessType;

	/**
	 * 事件名称
	 */
	@ApiModelProperty("事件名称")
	private String eventName;

	/**
	 * 触发代号
	 */
	@ApiModelProperty("触发类型code")
	private Integer triggerCode;

	/**
	 * 类别代号
	 */
	@ApiModelProperty("攻防类型code")
	private List<Integer> categoryCode;

	/**
	 * 行动代号
	 */
	@ApiModelProperty("动作类型code")
	private Integer actionCode;

	/**
	 * 源id
	 */
	@ApiModelProperty("源节点id")
	private String sourceId;

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
}
