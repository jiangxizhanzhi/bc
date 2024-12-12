package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 拓扑学
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class Topology implements Serializable {

	/**
	 * 元素
	 */
	private List<TopoElement> elements;
}
