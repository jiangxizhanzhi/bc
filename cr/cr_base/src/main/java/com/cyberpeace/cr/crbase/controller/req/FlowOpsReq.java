package com.cyberpeace.cr.crbase.controller.req;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 流老年 退休金 计划请求体
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
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
	 * 流id
	 */
	@ApiModelProperty("流量id")
	private String flowId;
}
