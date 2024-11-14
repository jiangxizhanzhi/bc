package com.cyberpeace.cr.redvsblue.service.context;

import java.io.Serializable;

import lombok.Data;

/**
 * 红蓝对抗态势服务器
 *
 * @author SN
 * @date 2024/11/14
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
	 * 岗位
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
