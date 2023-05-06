package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

import lombok.Data;

/**
 * 场景集合
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class SceneCollection implements Serializable {

	/**
	 * 使流收集
	 */
	private Boolean enableFlowCollect = false;

	/**
	 * 使探测器收集
	 */
	private Boolean enableProbeCollect;
}
