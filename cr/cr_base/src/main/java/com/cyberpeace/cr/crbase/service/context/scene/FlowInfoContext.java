package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 流量简要信息
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class FlowInfoContext implements Serializable {

	/**
	 * 流id
	 */
	private String flowId;

	/**
	 * 源ip地址
	 */
	private String sourceIp;
}
