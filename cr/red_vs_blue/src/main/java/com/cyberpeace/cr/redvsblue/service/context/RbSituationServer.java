package com.cyberpeace.cr.redvsblue.service.context;

import lombok.Data;

import java.io.Serializable;

/**
 * RB 情况服务器
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class RbSituationServer implements Serializable {

	/**
	 * 服务器id
	 */
	private String serverId;

	/**
	 * 服务器名称
	 */
	private String serverName;

	/**
	 * 角色
	 */
	private String role;

	/**
	 * 网络id
	 */
	private String networkId;

	/**
	 * 网络名称
	 */
	private String networkName;
}
