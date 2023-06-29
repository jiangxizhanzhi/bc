package com.cyberpeace.situation.constant.ws;

import lombok.Getter;

/**
 * WS消息类型
 *
 * @author chenhy
 * @date 2023/05/06
 */
public enum MessageType {

	/**
	 * 单节点动效
	 */
	SingleNodeDynamic(1, "single_node_dynamic"),

	/**
	 * 多节点动效
	 */
	MultiNodeDynamic(2, "multi_node_dynamic"),

	/**
	 * 攻击事件
	 */
	AttackEvent(3, "attack_event"),

	/**
	 * 节点对节点攻击事件
	 */
	AttackEventEE(4, "attack_event_element-element"),

	/**
	 * 节点对区域攻击事件
	 */
	AttackEventEA(5, "attack_event_element-area"),

	/**
	 * 攻击效果
	 */
	AttackEffect(6, "attack_effect"),

	/**
	 * 链路状态
	 */
	LinkStatus(7, "link_status"),

	/**
	 * 攻击方案
	 */
	AttackSchedule(8, "attack_schedule"),

	/**
	 * 场景配置
	 */
	SceneConfig(9, "scene_config"),

	/**
	 * 参数变更
	 */
	ChangePara(10, "change_para"),

	/**
	 * 任务事件
	 */
	TaskEvent(11, "task_event"),

	/**
	 * 任务事件-解题
	 */
	TaskEventJt(12, "task_event_jt"),

	/**
	 * 攻防事件
	 */
	TaskEventAd(13, "event_ad"),

	/**
	 * 节点状态
	 */
	TaskEventNode(14, "node_status"),

	/**
	 * 返回首页
	 */
	BackToHome(15, "back_home"),

	/**
	 * 跳转页面
	 */
	JumpPage(16, "jump_page"),

	/**
	 * 节点迁移
	 */
	NodeMigration(17, "node_migration"),

	/**
	 * 任务状态
	 */
	TaskStatus(18, "status"),

	/**
	 * 消息
	 */
	Message(19, "message"),

	/**
	 * 地图数据源
	 */
	MapSource(20, "map_source"),

	/**
	 * 跨层事件
	 */
	LayerEvent(21, "event_layer"),

	/**
	 * 拓扑变更
	 */
	ChangeTopo(22, "change_topo"),

	/**
	 * 链路状态
	 */
	TaskEventLink(23, "connection_status"),

	/**
	 * 刷新大屏所有接口
	 */
	REFRESH(24, "refresh");

	/**
	 * 代码
	 */
	@Getter
	private Integer code;

	/**
	 * 描述
	 */
	@Getter
	private String desc;

	MessageType(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

}
