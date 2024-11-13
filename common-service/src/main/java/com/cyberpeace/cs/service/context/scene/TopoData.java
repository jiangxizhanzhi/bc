package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

import lombok.Data;

/**
 * 拓扑数据
 *
 * @author SN
 * @date 2024/11/11
 */
@Data
public class TopoData implements Serializable {

	/**
	 * 拓扑
	 */
	private Topology topology;

	/**
	 * 插件
	 */
	private ScenePlugins plugins;

}
