package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 拓扑学
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class Topology implements Serializable {

	/**
	 * 元素
	 *
	 * @see List<TopoElement>
	 */
	private List<TopoElement> elements;
}
