package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

/**
 * 场景数据
 *
 * @author SN
 * @date 2024/11/11
 */
public interface SceneData extends Serializable {

	SceneTopology getTopology();

	ScenePlugins getPlugins();

	Boolean isStopped();

	Boolean isPause();

	Boolean isUnPause();

	Boolean isFinish();
}
