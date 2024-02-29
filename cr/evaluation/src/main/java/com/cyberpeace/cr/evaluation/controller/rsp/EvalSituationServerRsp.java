package com.cyberpeace.cr.evaluation.controller.rsp;

import lombok.Data;

import java.io.Serializable;

@Data
public class EvalSituationServerRsp implements Serializable {

	private String serverId;

	private String serverName;

}
