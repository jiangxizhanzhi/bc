package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.cyberpeace.situation.model.ws.WsMultiNodeDynamic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 事件消息请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class EventMessageReq implements Serializable {

	/**
	 * 场景id
	 *
	 * @see String
	 */
	@ApiModelProperty("场景id")
	@NotEmpty
	private String sceneId;

	/**
	 * 消息类型
	 *
	 * @see String
	 */
	@ApiModelProperty("消息类型")
	private String code;

	/**
	 * 消息内容
	 *
	 * @see WsMultiNodeDynamic
	 */
	@ApiModelProperty("消息内容")
	private WsMultiNodeDynamic data;

}
