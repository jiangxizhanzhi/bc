package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 场景拓扑学
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
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
