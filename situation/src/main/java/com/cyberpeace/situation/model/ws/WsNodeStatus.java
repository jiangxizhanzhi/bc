package com.cyberpeace.situation.model.ws;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.situation.service.context.ConnectionStatusContext;
import com.cyberpeace.situation.service.context.ElementStatusContext;
import com.google.common.collect.Lists;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * WS节点状态
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class WsNodeStatus implements Serializable {

	/**
	 * 节点状态列表
	 *
	 * @see List<ElementStatusContext>
	 */
	@ApiModelProperty("节点状态列表")
	private List<ElementStatusContext> elementStatus = Lists.newArrayList();

	/**
	 * 链路状态
	 *
	 * @see List<ConnectionStatusContext>
	 */
	@ApiModelProperty("链路状态")
	private List<ConnectionStatusContext> connectionStatus = Lists.newArrayList();

}
