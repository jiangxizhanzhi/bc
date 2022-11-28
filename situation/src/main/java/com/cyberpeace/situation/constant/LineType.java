package com.cyberpeace.situation.constant;

import com.cyberpeace.common.model.CommonContext;

import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.situation.model.LocalType;
import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

public enum LineType {

	/**
	 * 线型
	 */

	STRAIGHT("straight", "实直线"),
	DASHED_STRAIGHT("dashed_straight", "虚直线"),
	FLY("fly", "实飞线"),
	DASHED_FLY("dashed_fly", "虚飞线");

	@Getter
	private String code;

	@Getter
	private String desc;

	LineType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static String getRandomValue(LineType[] values) {

		return values[(int) (Math.random() * values.length)].code;

	}

	public String getMsg() {
		return CommonContext.isChinese() ? desc : code;
	}

	public static List<LocalType> getMsgList(){
		return StreamApiUtils.map(Lists.newArrayList(LineType.values()),
				lineType -> new LocalType(lineType.getCode(), lineType.getMsg()));
	}
}
