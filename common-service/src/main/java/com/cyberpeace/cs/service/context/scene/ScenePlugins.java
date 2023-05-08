package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

import lombok.Data;

/**
 * 场景插件
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Data
public class ScenePlugins implements Serializable {

	private CpFlow cpFlow;

	private CpCollection cpCollection;
}
