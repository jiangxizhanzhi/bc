package com.cyberpeace.integratation.service.bo;

import java.io.Serializable;

import lombok.Data;

/**
 * 用于设置中台拓扑的部分启动
 */
@Data
public class TopoSupportPartialStartContext implements Serializable {

	private String id;

	private Boolean support_partial_start;

	public TopoSupportPartialStartContext(String topoId, Boolean supportPartialStart) {
		this.id = topoId;
		this.support_partial_start = supportPartialStart;
	}

}
