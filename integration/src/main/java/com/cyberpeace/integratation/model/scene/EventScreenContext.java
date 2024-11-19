package com.cyberpeace.integratation.model.scene;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 事件屏幕上下文
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class EventScreenContext implements Serializable {

	/**
	 * 防御类型
	 *
	 * @see List<String>
	 */
	private List<String> defenseTypes;

	/**
	 * 启动时间
	 *
	 * @see String
	 */
	private String startTime;

	/**
	 * 结束时间
	 *
	 * @see String
	 */
	private String endTime;

	/**
	 * 事件名称
	 *
	 * @see List<String>
	 */
	private List<String> eventName;

	/**
	 * 源节点id列表
	 *
	 * @see List<String>
	 */
	private List<String> sourceNodeIds;

	/**
	 * 目标节点id列表
	 *
	 * @see List<String>
	 */
	private List<String> targetNodeIds;
}
