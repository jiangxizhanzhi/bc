package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;

import lombok.Data;

/**
 * 流量简要信息
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class FlowInfoContext implements Serializable {

	/**
	 * 流量id
	 *
	 * @see String
	 */
	private String flowId;

	/**
	 * 源ip地址
	 *
	 * @see String
	 */
	private String sourceIp;
}
