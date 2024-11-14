package com.cyberpeace.integratation.model.scene;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 事件屏幕上下文
 *
 * @author SN
 * @date 2024/11/14
 */
@Data
public class EventScreenContext implements Serializable {

	/**
	 * 防御类型
	 */
	private List<String> defenseTypes;

	/**
	 * 启动时间
	 */
	private String startTime;

	/**
	 * 结束时间
	 */
	private String endTime;

	/**
	 * 事件名称
	 */
	private List<String> eventName;

	/**
	 * 源节点id列表
	 */
	private List<String> sourceNodeIds;

	/**
	 * 目标节点id列表
	 */
	private List<String> targetNodeIds;
}
