package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

import lombok.Data;

/**
 * 场景收集
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class SceneCollection implements Serializable {

	/**
	 * 启用 Flow collect
	 */
	private Boolean enableFlowCollect = false;

	/**
	 * 启用 Probe Collect
	 */
	private Boolean enableProbeCollect;
}
