package com.cyberpeace.iov.index.service.bo.can;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MsgSendReq {

	@ApiModelProperty(value = "报文序列发送的时间间隔，单位/ms")
	private String msgInterval;

	private List<CanMsg> msgs;
}
