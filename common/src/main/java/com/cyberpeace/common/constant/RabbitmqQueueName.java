package com.cyberpeace.common.constant;

/**
 * rabbitmq监听的队列名统一管理
 */
public final class RabbitmqQueueName {

	/**
	 * 场景状态变更
	 */
	public static final String SCENE_STATUS_CHANGE = "";

	/**
	 * 场景websocket动态事件
	 */
	public static final String SCENE_EVENT_WEBSOCKET = "cn.cyberpeace.mp.cscene_scene_report";

	private RabbitmqQueueName() {}

}
