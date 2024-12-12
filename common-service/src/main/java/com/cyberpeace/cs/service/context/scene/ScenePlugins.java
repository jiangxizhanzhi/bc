package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

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
}
