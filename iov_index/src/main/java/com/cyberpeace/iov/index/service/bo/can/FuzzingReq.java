package com.cyberpeace.iov.index.service.bo.can;

import lombok.Data;

@Data
public class FuzzingReq {

	private String msgId = "-1";

	private String msgChn = "1";

	private String msgData = "XXXXXXXXXXXXXXXX";
}
