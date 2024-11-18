package com.cyberpeace.cr.redvsblue.service.context;

import lombok.Data;

import java.io.Serializable;

/**
 * RB 情况服务器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class RbSituationServer implements Serializable {

	/**
	 * 服务器id
	 *
	 * @see String
	 */
	private String serverId;

	/**
	 * 服务器名称
	 *
	 * @see String
	 */
	private String serverName;

	/**
	 * 角色
	 *
	 * @see String
	 */
	private String role;

	/**
	 * 网络id
	 *
	 * @see String
	 */
	private String networkId;

	/**
	 * 网络名称
	 *
	 * @see String
	 */
	private String networkName;
}
