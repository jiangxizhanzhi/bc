package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;

/**
 * CP 流量
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class CpFlow implements Serializable {

	/**
	 * 元素
	 *
	 * @see Map<String, ElementFlowList>
	 */
	private Map<String, ElementFlowList> elements;

}
