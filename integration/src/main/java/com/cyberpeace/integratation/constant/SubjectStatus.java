package com.cyberpeace.integratation.constant;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

import lombok.Getter;

public enum SubjectStatus {

	/**
	 * 科目状态
	 */
	QT("0", "未知"),
	WKS("1", "未开始"),
	CSH("2", "初始化完成"),
	JXZ("3", "进行中"),
	ZTZ("4", "暂停中"),
	ZZ("5", "终止"),
	YWC("6", "已完成");

	@Getter
	private String code;

	@Getter
	private String name;

	SubjectStatus(String code, String name) {
		this.code = code;
		this.name = name;
	}

	public static List<HashMap<String, String>> getSubjectStatusMap() {
		List<HashMap<String, String>> list = new ArrayList<>();
		for (SubjectStatus taskType : EnumSet.allOf(SubjectStatus.class)) {
			HashMap<String, String> map = new HashMap<>();
			map.put("name", taskType.name);
			map.put("code", taskType.code);
			list.add(map);
		}
		return list;
	}
}
