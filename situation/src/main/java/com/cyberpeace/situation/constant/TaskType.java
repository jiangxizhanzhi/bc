package com.cyberpeace.situation.constant;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

import lombok.Getter;

public enum TaskType {

	/**
	 * 任务类型
	 */

	QB(null, "全部"),
	XNJD("SY01", "性能鉴定"),
	XNRZ("SY02", "性能认证"),
	HLDK("XL05", "红蓝对抗"),
	ZZSY("XL01", "ZZ试验"),
	DQJS("XL03", "夺旗竞赛"),
	ZZXL("XL04", "自主训练"),
	ZXBW("XL02", "专项比武");

	@Getter
	private String code;

	@Getter
	private String name;

	TaskType(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public static List<HashMap<String, String>> getTaskTypeMap() {
		List<HashMap<String, String>> list = new ArrayList<>();
		for (TaskType taskType : EnumSet.allOf(TaskType.class)) {
			HashMap<String, String> map = new HashMap<>();
			map.put("name", taskType.name);
			map.put("code", taskType.code);
			list.add(map);
		}
		return list;
	}
}
