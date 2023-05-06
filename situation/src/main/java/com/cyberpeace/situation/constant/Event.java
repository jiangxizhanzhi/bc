package com.cyberpeace.situation.constant;

import java.util.List;

import com.google.common.collect.Lists;

import lombok.Getter;

/**
 * 场景事件
 *
 * @author chenhy
 * @date 2023/05/06
 */
public enum Event {

	/**
	 * 未使用
	 */
	UNUSE(-1, "未使用"),

	/**
	 * 默认
	 */
	DEFAULT(0, "默认"),

	/**
	 * 初始化
	 */
	INITIATE(1, "初始化"),

	/**
	 * 创建
	 */
	CREATE(2, "创建"),

	/**
	 * 启动
	 */
	START(3, "启动"),

	/**
	 * 停止
	 */
	STOP(4, "停止"),

	/**
	 * 重启
	 */
	RESTART(5, "重启"),

	/**
	 * 挂起
	 */
	PAUSE(6, "挂起"),

	/**
	 * 恢复
	 */
	UNPAUSE(7, "恢复"),

	/**
	 * 快照
	 */
	SNAPSHOT(8, "快照"),

	/**
	 * 删除
	 */
	DELETE(9, "删除"),

	/**
	 * 修改
	 */
	MODIFY(10, "修改"),

	/**
	 * 暂停
	 */
	SUSPEND(12, "暂停"),

	/**
	 * 恢复
	 */
	RESUME(13, "恢复");

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

	Event(Integer code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	/**
	 * 获取未启动场景事件代码列表
	 *
	 * @return {@code List<Integer>}
	 */
	public static List<Integer> getUnstartedCodeList() {
		return Lists.newArrayList(UNPAUSE.code, STOP.code, PAUSE.code, DELETE.code, SUSPEND.code);
	}

	/**
	 * 获取创建场景事件代码列表
	 *
	 * @return {@code List<Integer>}
	 */
	public static List<Integer> getCreatingCodeList() {
		return Lists.newArrayList(CREATE.code, MODIFY.code);
	}

	/**
	 * 获取启动场景事件代码列表
	 *
	 * @return {@code List<Integer>}
	 */
	public static List<Integer> getStartingCodeList() {
		return Lists.newArrayList(START.code, RESTART.code, UNPAUSE.code, RESUME.code);
	}

}
