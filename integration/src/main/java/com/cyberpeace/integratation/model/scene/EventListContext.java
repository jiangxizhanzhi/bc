package com.cyberpeace.integratation.model.scene;

import lombok.Data;

import java.io.Serializable;

/**
 * 事件列表上下文
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EventListContext implements Serializable {

	/**
	 * 流量
	 */
	private EventScreenContext flow;

	/**
	 * 主机
	 */
	private EventScreenContext host;

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
}
