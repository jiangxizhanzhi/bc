package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;
import java.util.List;

/**
 * 场景拓扑
 *
 * @author SN
 * @date 2024/11/11
 */
public interface SceneTopology extends Serializable {

	SceneStatus getScene();

	List<SceneElement> getElements();
}

