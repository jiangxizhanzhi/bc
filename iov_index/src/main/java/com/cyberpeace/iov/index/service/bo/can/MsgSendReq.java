package com.cyberpeace.iov.index.service.bo.can;

import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class MsgSendReq {

	@ApiModelProperty(value = "报文序列发送的时间间隔，单位/ms 取值10 20 30")
	@NotNull
	@NotBlank
	private String msgInterval;

	@ApiModelProperty(value = "报文列表")
	@Valid
	@NotNull
	@NotEmpty
	private List<CanMsg> msgs;
}
