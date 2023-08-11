package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;
import java.util.List;

public interface SceneTopology extends Serializable {

	SceneStatus getScene();

	List<SceneElement> getElements();
}

