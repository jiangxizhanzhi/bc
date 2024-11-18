package com.cyberpeace.cr.crbase.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 流量操作 计划请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class FlowOpsReq implements Serializable {

	/**
	 * 房间id
	 *
	 * @see String
	 */
	@ApiModelProperty("团队训练实例id")
	private String roomId;

	/**
	 * 节点id
	 *
	 * @see String
	 */
	@ApiModelProperty("节点id")
	private String nodeId;

	/**
	 * 流量id
	 *
	 * @see String
	 */
	@ApiModelProperty("流量id")
	private String flowId;
}
