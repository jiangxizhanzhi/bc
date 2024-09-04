package com.cyberpeace.iov.index.service.bo.can;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class FuzzingReq {

	@NotBlank
	@NotNull
	@Size(min = 3, max = 3, message = "参数长度必须为3个字符")
	private String msgId;

	private String msgChn;

	@NotBlank
	@NotNull
	@Size(min = 16, max = 16, message = "参数长度必须为16个字符")
	private String msgData;
}
