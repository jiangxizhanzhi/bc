package com.cyberpeace.integratation.model.scene;

import java.io.Serializable;

import lombok.Data;

/**
 * 流程信息
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class FlowInfo implements Serializable {

	/**
	 * 流id
	 */
	private String flow_id;

	/**
	 * 源ip地址
	 */
	private String source_ip;

	public void setFlowId(String flowId) {
		this.flow_id = flowId;
	}

	public void setId(String flowId) {
		this.flow_id = flowId;
	}

	public void setSourceIp(String sourceIp) {
		this.source_ip = sourceIp;
	}
}
