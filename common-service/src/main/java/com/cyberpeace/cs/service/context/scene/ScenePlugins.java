package com.cyberpeace.cs.service.context.scene;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 场景插件
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class ScenePlugins implements Serializable {

	/**
	 * CP 流
	 */
	private CpFlow cpFlow;

	/**
	 * CP 集合
	 */
	private CpCollection cpCollection;

	private CpCloud cpcloud;
}
