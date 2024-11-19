package com.cyberpeace.situation.constant;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Getter;

/**
 * 事件
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Enum
 * @since 1.0.0
 */
public enum Event {

	/**
	 * 未使用
	 *
	 * @see Event
	 */
	UNUSE(-1, "未使用"),
	/**
	 * 默认
	 *
	 * @see Event
	 */
	DEFAULT(0, "默认"),
	/**
	 * 初始化
	 *
	 * @see Event
	 */
	INITIATE(1, "初始化"),
	/**
	 * 创建
	 *
	 * @see Event
	 */
	CREATE(2, "创建"),
	/**
	 * 启动
	 *
	 * @see Event
	 */
	START(3, "启动"),
	/**
	 * 停止
	 *
	 * @see Event
	 */
	STOP(4, "停止"),
	/**
	 * 重启
	 *
	 * @see Event
	 */
	RESTART(5, "重启"),
	/**
	 * 挂起
	 *
	 * @see Event
	 */
	PAUSE(6, "挂起"),
	/**
	 * 恢复
	 *
	 * @see Event
	 */
	UNPAUSE(7, "恢复"),
	/**
	 * 快照
	 *
	 * @see Event
	 */
	SNAPSHOT(8, "快照"),
	/**
	 * 删除
	 *
	 * @see Event
	 */
	DELETE(9, "删除"),
	/**
	 * 修改
	 *
	 * @see Event
	 */
	MODIFY(10, "修改"),
	/**
	 * 暂停
	 *
	 * @see Event
	 */
	SUSPEND(12, "暂停"),
	/**
	 * 恢复
	 *
	 * @see Event
	 */
	RESUME(13, "恢复");

	/**
	 * 代号
	 *
	 * @see Integer
	 */
	@Getter
	private Integer code;

	/**
	 * 描述
	 *
	 * @see String
	 */
	@Getter
	private String desc;

	Event(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static List<Integer> getUnstartedCodeList() {
		return Lists.newArrayList(UNPAUSE.code, STOP.code, PAUSE.code, DELETE.code, SUSPEND.code);
	}

	public static List<Integer> getCreatingCodeList() {
		return Lists.newArrayList(CREATE.code, MODIFY.code);
	}

	public static List<Integer> getStartingCodeList() {
		return Lists.newArrayList(START.code, RESTART.code, UNPAUSE.code, RESUME.code);
	}

}
