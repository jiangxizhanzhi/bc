package com.cyberpeace.integratation.model.scene;

import lombok.Data;

import java.io.Serializable;

/**
 * 事件列表上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class EventListContext implements Serializable {

	/**
	 * 流量
	 *
	 * @see EventScreenContext
	 */
	private EventScreenContext flow;

	/**
	 * 主机
	 *
	 * @see EventScreenContext
	 */
	private EventScreenContext host;

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
}
