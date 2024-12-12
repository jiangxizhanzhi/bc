package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * CP 集合
 *
 * @author SN
 * @date 2024/11/08
 */
@Getter
@Setter
public class CpCollection implements Serializable {

	/**
	 * 场景
	 */
	private SceneCollection scene;

}
