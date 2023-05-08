package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.cyberpeace.situation.model.ws.WsMultiNodeDynamic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 事件消息请求
 *
 * @author chenhy
 * @date 2023/05/06
 */
@Data
public class EventMessageReq implements Serializable {

	/**
	 * 场景id
	 */
	@ApiModelProperty("场景id")
	@NotEmpty
	private String sceneId;

	/**
	 * 消息类型
	 */
	@ApiModelProperty("消息类型")
	private String code;

	/**
	 * 消息内容
	 */
	@ApiModelProperty("消息内容")
	private WsMultiNodeDynamic data;

}
