package com.cyberpeace.situation.constant;

import java.util.EnumSet;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import com.alibaba.fastjson.JSONObject;

import lombok.Getter;

public enum DynamicParameter {

	/**
	 * 任务动态参数
	 */

	RWID("RWID", "task_id", "任务ID"),
	KMID("KMID", "subject_id", "科目ID"),
	FBCID("FBCID", "sub_range_id", "分靶场ID"),
	KSSJ("KSSJ", "start_time", "开始时间"),
	JSSJ("JSSJ", "end_time", "结束时间"),
	YHJS("YHJS", "role", "角色");

	@Getter
	private String code;

	@Getter
	private String field;

	@Getter
	private String name;

	DynamicParameter(String code, String field, String name) {
		this.code = code;
		this.field = field;
		this.name = name;
	}



	public static List<JSONObject> getTaskDynamicParameter() {

		List<JSONObject> dynamicParameters = Lists.newArrayList();

		for (DynamicParameter dynamicParameter : EnumSet.allOf(DynamicParameter.class)) {

			JSONObject para = new JSONObject();
			para.put("code", dynamicParameter.code);
			para.put("field", dynamicParameter.field);
			para.put("name", dynamicParameter.name);
			dynamicParameters.add(para);
		}
		return dynamicParameters;
	}
}
