package com.cyberpeace.cs.service.context.scene;

import java.io.Serializable;

import lombok.Data;

/**
 * 场景状态
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SceneStatus implements Serializable {

	/**
	 * 事件
	 *
	 * @see Integer
	 */
	private Integer event;

	/**
	 * 事件状态
	 *
	 * @see Integer
	 */
	private Integer eventStatus;
}
