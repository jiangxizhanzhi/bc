package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

/**
 * 场景插件
 *
 * @author SN
 * @date 2024/11/11
 */
public interface ScenePlugins extends Serializable {

	Cpflow getCpflow();

	Cpcollection getCpcollection();
}


