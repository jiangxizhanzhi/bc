package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

import lombok.Data;

/**
 * CP 集合
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class CpCollection implements Serializable {

	/**
	 * 场景
	 */
	private SceneCollection scene;

}
