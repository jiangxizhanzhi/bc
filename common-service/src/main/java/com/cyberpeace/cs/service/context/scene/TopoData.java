package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

import lombok.Data;

@Data
public class TopoData implements Serializable {

	private Topology topology;

	private ScenePlugins plugins;

}
