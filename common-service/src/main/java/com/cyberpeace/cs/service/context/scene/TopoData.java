package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

import lombok.Data;

/**
 * 拓扑数据
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class TopoData implements Serializable {

	/**
	 * 拓扑学
	 *
	 * @see Topology
	 */
	private Topology topology;

	/**
	 * 插件
	 *
	 * @see ScenePlugins
	 */
	private ScenePlugins plugins;

}
