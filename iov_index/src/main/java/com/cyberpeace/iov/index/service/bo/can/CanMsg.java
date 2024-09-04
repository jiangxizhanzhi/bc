package com.cyberpeace.iov.index.service.bo.can;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CanMsg {
	@ApiModelProperty(value = "报文ID")
	@NotBlank
	@NotNull
	@Size(min = 3, max = 3, message = "参数长度必须为3个字符")
	private String msgId;

	@ApiModelProperty(value = "报文通道，后台指定")
	private String msgChn;

	// 默认一般是8
	@ApiModelProperty(value = "报文长度，默认8")
	private String msgDlc = "8";

	@ApiModelProperty(value = "报文数据")
	@NotBlank
	@NotNull
	@Size(min = 16, max = 16, message = "参数长度必须为16个字符")
	private String msgData;
}
