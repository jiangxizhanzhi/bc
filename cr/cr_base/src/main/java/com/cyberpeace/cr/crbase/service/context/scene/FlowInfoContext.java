package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;

import lombok.Data;

/**
 * 流量简要信息
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class FlowInfoContext implements Serializable {

	/**
	 * 流量id
	 */
	private String flowId;

	/**
	 * 源ip地址
	 */
	private String sourceIp;
}
