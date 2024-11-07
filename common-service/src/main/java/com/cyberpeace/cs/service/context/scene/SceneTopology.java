package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 场景拓扑学
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class SceneTopology implements Serializable {

	/**
	 * 场景
	 */
	private SceneStatus scene;

	/**
	 * 元素
	 */
	private List<SceneElement> elements;

}
