package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

import lombok.Data;

/**
 * 场景收集
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SceneCollection implements Serializable {

	/**
	 * 启用 Flow collect
	 *
	 * @see Boolean
	 */
	private Boolean enableFlowCollect = false;

	/**
	 * 启用 Probe Collect
	 *
	 * @see Boolean
	 */
	private Boolean enableProbeCollect;
}
