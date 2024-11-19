package com.cyberpeace.integratation.service.bo;

import java.io.Serializable;

import lombok.Data;

/**
 * 用于设置中台拓扑的部分启动
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class TopoSupportPartialStartContext implements Serializable {

	/**
	 * id
	 *
	 * @see String
	 */
	private String id;

	/**
	 * 支持部分启动
	 *
	 * @see Boolean
	 */
	private Boolean support_partial_start;

	public TopoSupportPartialStartContext(String topoId, Boolean supportPartialStart) {
		this.id = topoId;
		this.support_partial_start = supportPartialStart;
	}

}
