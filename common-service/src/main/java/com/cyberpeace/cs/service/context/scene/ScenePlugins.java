package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

import lombok.Data;

/**
 * 场景插件
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class ScenePlugins implements Serializable {

	/**
	 * CP 流量
	 *
	 * @see CpFlow
	 */
	private CpFlow cpFlow;

	/**
	 * CP 集合
	 *
	 * @see CpCollection
	 */
	private CpCollection cpCollection;
}
