package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 元素流列表
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class ElementFlowList implements Serializable {

	/**
	 * 流
	 */
	private List<ElementFlow> flows;
}
