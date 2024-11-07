package com.cyberpeace.common.constant;

/**
 * rabbitmq监听的队列名统一管理
 *
 * @author SN
 * @date 2024/11/07
 */
public interface RabbitmqQueueName {

	/**
	 * 场景状态变更
	 */
	String SCENE_STATUS_CHANGE = "";

	/**
	 * 场景websocket动态事件
	 */
	String SCENE_EVENT_WEBSOCKET = "cn.cyberpeace.mp.cscene_scene_report";

}
