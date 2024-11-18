package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

/**
 * 场景拓扑学
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SceneTopology implements Serializable {

	/**
	 * 场景
	 *
	 * @see SceneStatus
	 */
	private SceneStatus scene;

	/**
	 * 元素
	 *
	 * @see List<SceneElement>
	 */
	private List<SceneElement> elements;

}
