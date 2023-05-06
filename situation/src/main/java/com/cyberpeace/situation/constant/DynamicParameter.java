package com.cyberpeace.situation.constant;

import java.util.EnumSet;
import java.util.List;

import org.apache.commons.compress.utils.Lists;

import com.alibaba.fastjson.JSONObject;

import lombok.Getter;

/**
 * 大屏动态参数
 *
 * @author chenhy
 * @date 2023/05/06
 */
public enum DynamicParameter {

	/**
	 * 任务ID
	 */
	RWID("RWID", "task_id", "任务ID"),

	/**
	 * 科目ID
	 */
	KMID("KMID", "subject_id", "科目ID"),

	/**
	 * 分靶场ID
	 */
	FBCID("FBCID", "sub_range_id", "分靶场ID"),

	/**
	 * 开始时间
	 */
	KSSJ("KSSJ", "start_time", "开始时间"),

	/**
	 * 结束时间
	 */
	JSSJ("JSSJ", "end_time", "结束时间"),

	/**
	 * 大屏时间
	 */
	DPSJ("DPSJ", "current_time", "大屏时间"),

	/**
	 * 角色
	 */
	YHJS("YHJS", "role", "角色"),

	/**
	 * 大屏ID
	 */
	DPID("DPID", "dashboard_id", "大屏ID"),

	/**
	 * 特征ID
	 */
	TZID("TZID", "feature_id", "特征ID");

	/**
	 * 代码
	 */
	@Getter
	private String code;

	/**
	 * 字段
	 */
	@Getter
	private String field;

	/**
	 * 名称
	 */
	@Getter
	private String name;

	DynamicParameter(String code, String field, String name) {
		this.code = code;
		this.field = field;
		this.name = name;
	}

	/**
	 * 获取大屏动态参数列表
	 *
	 * @return {@code List<JSONObject>}
	 */
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
