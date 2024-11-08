package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

import lombok.Data;

/**
 * 场景状态
 *
 * @author SN
 * @date 2024/11/08
 */
@Data
public class SceneStatus implements Serializable {

	/**
	 * 事件
	 */
	private Integer event;

	/**
	 * 事件状态
	 */
	private Integer eventStatus;
}
