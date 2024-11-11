package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;
import java.util.Map;

import lombok.Data;

/**
 * CP 流量
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class CpFlow implements Serializable {

	/**
	 * 元素
	 */
	private Map<String, ElementFlowList> elements;

}
