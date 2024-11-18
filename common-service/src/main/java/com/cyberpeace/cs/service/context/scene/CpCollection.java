package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

import lombok.Data;

/**
 * CP 集合
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class CpCollection implements Serializable {

	/**
	 * 场景
	 *
	 * @see SceneCollection
	 */
	private SceneCollection scene;

}
