package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 元素流量列表
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class ElementFlowList implements Serializable {

	/**
	 * 流量
	 */
	private List<ElementFlow> flows;
}
