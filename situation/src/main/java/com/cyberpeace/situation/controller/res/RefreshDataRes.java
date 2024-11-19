package com.cyberpeace.situation.controller.res;

import java.util.List;

import com.cyberpeace.situation.model.ws.WsMessage;
import com.cyberpeace.situation.model.ws.WsNodeStatus;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 刷新数据结果
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Data
public class RefreshDataRes {

	/**
	 * 是否自动运行流量
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否自动运行流量")
	private Boolean flowAutoRunResult;

	/**
	 * 节点和链路状态
	 *
	 * @see WsMessage<WsNodeStatus>
	 */
	@ApiModelProperty("节点和链路状态")
	private WsMessage<WsNodeStatus> linkStatus;

	/**
	 * 流量事件
	 *
	 * @see List<WsMessage>
	 */
	@ApiModelProperty("流量事件")
	private List<WsMessage> flowEvents;

	/**
	 * 主机采集事件
	 *
	 * @see List<WsMessage>
	 */
	@ApiModelProperty("主机采集事件")
	private List<WsMessage> hostEvents;

}