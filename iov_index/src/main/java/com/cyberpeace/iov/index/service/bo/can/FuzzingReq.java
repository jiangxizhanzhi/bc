package com.cyberpeace.iov.index.service.bo.can;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class FuzzingReq {

	@NotBlank
	@NotNull
	@Size(min = 2, max = 3, message = "参数必须为-1或0-7FF")
	private String msgId;

	private String msgChn;

	@NotBlank
	@NotNull
	@Size(min = 16, max = 16, message = "参数长度必须为16个字符")
	private String msgData;
}
