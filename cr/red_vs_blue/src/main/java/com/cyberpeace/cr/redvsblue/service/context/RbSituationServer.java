package com.cyberpeace.cr.redvsblue.service.context;

import lombok.Data;

import java.io.Serializable;

@Data
public class RbSituationServer implements Serializable {

	private String serverId;

	private String serverName;

	private String role;

	private String networkId;

	private String networkName;
}
