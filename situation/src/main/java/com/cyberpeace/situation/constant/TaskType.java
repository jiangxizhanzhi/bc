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

	QB("", "全部", false),
	XNJD("SY01", "性能鉴定", false),
	XNRZ("SY02", "性能认证", false),
	HLDK("XL05", "红蓝对抗", true),
	ZZSY("XL01", "ZZ试验", true),
	DQJS("XL03", "夺旗竞赛", true),
	ZZXL("XL04", "自主训练", true),
	ZXBW("XL02", "专项比武", true);

	@Getter
	private String code;

	@Getter
	private String name;

	@Getter
	private Boolean support;

	TaskType(String code, String name, Boolean support) {
		this.code = code;
		this.name = name;
		this.support = support;
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
