package com.cyberpeace.cr.redvsblue.service.context;

import java.io.Serializable;

import lombok.Data;

@Data
public class RbSituationServer implements Serializable {

	private String serverId;

	private String serverName;

	private String role;

	private String networkId;

	private String networkName;
}
