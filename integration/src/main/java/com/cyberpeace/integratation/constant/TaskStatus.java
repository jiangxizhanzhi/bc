package com.cyberpeace.integratation.constant;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

import lombok.Getter;

public enum TaskStatus {

	/**
	 * 任务状态
	 */
	RWLX("RWZT01", "立项"),
	DSP("RWZT02", "待审批"),
	RWZB("RWZT03", "任务准备"),
	RWZX("RWZT04", "任务执行"),
	YWC("RWZT05", "已完成"),
	QT("RWZT99", "其它");

	@Getter
	private String code;

	@Getter
	private String name;

	TaskStatus(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public static List<HashMap<String, String>> getTaskStatusMap() {
		List<HashMap<String, String>> list = new ArrayList<>();
		for (TaskStatus taskType : EnumSet.allOf(TaskStatus.class)) {
			HashMap<String, String> map = new HashMap<>();
			map.put("name", taskType.name);
			map.put("code", taskType.code);
			list.add(map);
		}
		return list;
	}
}
