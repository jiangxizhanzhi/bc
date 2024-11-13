package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;
import java.util.List;

/**
 * 场景元素
 *
 * @author SN
 * @date 2024/11/11
 */
public interface SceneElement extends Serializable {

	String getId();

	String getName();

	List<Port> getPorts();
}

