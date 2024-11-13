package com.cyberpeace.cr.crbase.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 流量操作请求体
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class FlowOpsReq implements Serializable {

	/**
	 * 房间id
	 */
	@ApiModelProperty("团队训练实例id")
	private String roomId;

	/**
	 * 节点id
	 */
	@ApiModelProperty("节点id")
	private String nodeId;

	/**
	 * 流量id
	 */
	@ApiModelProperty("流量id")
	private String flowId;
}
