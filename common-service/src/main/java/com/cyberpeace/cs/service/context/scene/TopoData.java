package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

import lombok.Data;

/**
 * 拓扑数据
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class TopoData implements Serializable {

	private Topology topology;

	private ScenePlugins plugins;

}
