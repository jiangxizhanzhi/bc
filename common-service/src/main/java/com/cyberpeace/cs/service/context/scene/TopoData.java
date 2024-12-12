package com.cyberpeace.cs.service.context.scene;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 拓扑数据
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class TopoData implements Serializable {

	/**
	 * 拓扑学
	 */
	private Topology topology;

	/**
	 * 插件
	 */
	private ScenePlugins plugins;

}
