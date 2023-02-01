package com.cyberpeace.integratation.constant;

import lombok.Getter;

public enum TaskEvent {

	/**
	 * 任务事件
	 */
	RWXF("1", "任务下发"), RWKS("2", "任务开始"), RWJS("3", "任务结束");

	@Getter
	private String code;

	@Getter
	private String name;

	TaskEvent(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public static String getRandomValue(TaskEvent[] values) {
		return values[(int) (Math.random() * values.length)].name;
	}
}
