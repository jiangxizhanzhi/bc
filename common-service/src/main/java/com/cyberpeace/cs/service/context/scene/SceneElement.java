package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;
import java.util.List;

public interface SceneElement extends Serializable {

	String getId();

	String getName();

	List<Port> getPorts();
}

