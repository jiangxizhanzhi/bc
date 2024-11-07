package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 拓扑学
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class Topology implements Serializable {

	/**
	 * 元素
	 */
	private List<TopoElement> elements;
}
