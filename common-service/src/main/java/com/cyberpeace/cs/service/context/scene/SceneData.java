package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

public interface SceneData extends Serializable {

	SceneTopology getTopology();

	ScenePlugins getPlugins();

	Boolean isStopped();

	Boolean isPause();

	Boolean isUnPause();

	Boolean isFinish();
}
