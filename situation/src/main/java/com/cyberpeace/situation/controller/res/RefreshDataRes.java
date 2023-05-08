package com.cyberpeace.situation.controller.res;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.situation.model.ws.WsMessage;
import com.cyberpeace.situation.model.ws.WsNodeStatus;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 刷新数据返回
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class RefreshDataRes implements Serializable {

	/**
	 * 是否自动运行流量
	 */
	@ApiModelProperty("是否自动运行流量")
	private Boolean flowAutoRunResult;

	/**
	 * 节点和链路状态
	 */
	@ApiModelProperty("节点和链路状态")
	private WsMessage<WsNodeStatus> linkStatus;

	/**
	 * 流量事件
	 */
	@ApiModelProperty("流量事件")
	private List<WsMessage> flowEvents;

}