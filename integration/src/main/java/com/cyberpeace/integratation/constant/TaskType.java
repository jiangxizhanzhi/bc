package com.cyberpeace.integratation.constant;

import java.util.*;

import lombok.Getter;

public enum TaskType {

	/**
	 * 任务类型
	 */

	QB("", "全部", "全部",false, true),
	XNJD("SY01", "性能鉴定", "性能鉴定试验",false, true),
	XNRZ("SY02", "性能验证", "性能验证试验",false, true),
	SYZZ("SY03", "作战试验", "作战试验",false, true),
	QTSY("SY99", "其它试验", "其它试验",false, false),
	HLDK("XL03", "红蓝对抗", "红蓝对抗",true, true),
	DQJS("XL02", "夺旗竞赛", "夺旗竞赛",true, true),
	ZZXL("XL04", "自主训练", "自主训练",true, true),
	ZXBW("XL01", "专项比武", "专项比武",true, true),
	QTXL("XL99", "其它训练", "其它训练",false, false);

	@Getter
	private String code;

	@Getter
	private String name;

	@Getter
	private String desc;

	@Getter
	private Boolean support;

	@Getter
	private Boolean main;

	TaskType(String code, String name, String desc, Boolean support, Boolean main) {
		this.code = code;
		this.name = name;
		this.desc = desc;
		this.support = support;
		this.main = main;
	}

	public static List<HashMap<String, String>> getTaskTypeMap(Boolean main) {
		List<HashMap<String, String>> list = new ArrayList<>();
		for (TaskType taskType : EnumSet.allOf(TaskType.class)) {
			if (main && !taskType.main){
				continue;
			}

			HashMap<String, String> map = new HashMap<>();
			map.put("name", taskType.name);
			map.put("code", taskType.code);
			list.add(map);
		}
		return list;
	}

	public static HashMap<String, String> getTaskType(){
		HashMap<String, String> hashMap = new HashMap<>();
		for (TaskType taskType : EnumSet.allOf(TaskType.class)) {
			HashMap<String, String> map = new HashMap<>();
			map.put("name", taskType.name);
			map.put("code", taskType.code);
		}
		return hashMap;
	}

	public static <T extends TaskType> T getEnumByCode(String name, Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).filter(t -> t.getName().equals(name))
				.findFirst().orElse(null);
	}
}
