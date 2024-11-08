package com.cyberpeace.integratation.service.bo;

import lombok.Data;

import java.io.Serializable;

/**
 * 用于设置中台拓扑的部分启动
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class TopoSupportPartialStartContext implements Serializable {

	/**
	 * id
	 */
	private String id;

	/**
	 * 支持 Partial Start
	 */
	private Boolean support_partial_start;

	public TopoSupportPartialStartContext(String topoId, Boolean supportPartialStart) {
		this.id = topoId;
		this.support_partial_start = supportPartialStart;
	}

}
