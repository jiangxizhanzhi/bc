package com.cyberpeace.integratation.model.scene;

import lombok.Data;

import java.io.Serializable;

/**
 * 场景事件上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class SceneEventContext implements Serializable {

	/**
	 * 分页
	 *
	 * @see Integer
	 */
	private Integer page;

	/**
	 * 分页大小
	 *
	 * @see Integer
	 */
	private Integer pageSize;

	/**
	 * 场景id
	 *
	 * @see String
	 */
	private String sceneId;

	/**
	 * 流量
	 *
	 * @see Boolean
	 */
	private Boolean flow;

	/**
	 * 主机
	 *
	 * @see Boolean
	 */
	private Boolean host;

	/**
	 * 攻击
	 *
	 * @see AttackScreenContext
	 */
	private AttackScreenContext attack;

	/**
	 * 防御
	 *
	 * @see AttackScreenContext
	 */
	private AttackScreenContext defense;

}
