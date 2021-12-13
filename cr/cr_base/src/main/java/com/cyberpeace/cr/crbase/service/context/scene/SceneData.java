package com.cyberpeace.cr.crbase.service.context.scene;

import java.io.Serializable;

import lombok.Data;

@Data
public class SceneData implements Serializable {

	private SceneTopology topology;

	private ScenePlugins plugins;

}
