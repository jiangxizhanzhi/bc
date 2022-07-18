package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotEmpty;

import com.cyberpeace.situation.model.ws.WsMultiNodeDynamic;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EventMessageReq implements Serializable {

	@ApiModelProperty("场景id")
	@NotEmpty
	private String sceneId;

	@ApiModelProperty("消息类型")
	private String code;

	@ApiModelProperty("消息内容")
	private WsMultiNodeDynamic data;

}
