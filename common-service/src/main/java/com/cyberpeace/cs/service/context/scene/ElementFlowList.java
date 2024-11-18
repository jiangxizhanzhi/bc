package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 元素流量列表
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class ElementFlowList implements Serializable {

	/**
	 * 流量
	 *
	 * @see List<ElementFlow>
	 */
	private List<ElementFlow> flows;
}
