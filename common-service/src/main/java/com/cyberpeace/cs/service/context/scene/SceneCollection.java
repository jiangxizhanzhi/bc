package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

/**
 * 场景收集
 *
 * @author SN
 * @date 2024/11/11
 */
public interface SceneCollection extends Serializable {

	Boolean getEnable_flow_collect();

	Boolean getEnable_probe_collect();
}

