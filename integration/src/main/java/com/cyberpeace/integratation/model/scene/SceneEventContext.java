package com.cyberpeace.integratation.model.scene;

import lombok.Data;

import java.io.Serializable;

/**
 * 场景事件上下文
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class SceneEventContext implements Serializable {

	/**
	 * 分页
	 */
	private Integer page;

	/**
	 * 分页大小
	 */
	private Integer pageSize;

	/**
	 * 场景id
	 */
	private String sceneId;

	/**
	 * 流量
	 */
	private Boolean flow;

	/**
	 * 主机
	 */
	private Boolean host;

	/**
	 * 攻击
	 */
	private AttackScreenContext attack;

	/**
	 * 防御
	 */
	private AttackScreenContext defense;

}
