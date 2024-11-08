package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 元素流列表
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class ElementFlowList implements Serializable {

	/**
	 * 流
	 */
	private List<ElementFlow> flows;
}
