package com.cyberpeace.situation.constant;

import com.cyberpeace.common.model.CommonContext;

import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.situation.model.LocalType;
import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

/**
 * 线型
 *
 * @author chenhy
 * @date 2023/05/06
 */
public enum LineType {

	/**
	 * 实直线
	 */
	STRAIGHT("straight", "实直线"),

	/**
	 * 虚直线
	 */
	DASHED_STRAIGHT("dashed_straight", "虚直线"),

	/**
	 * 实飞线
	 */
	FLY("fly", "实飞线"),

	/**
	 * 虚飞线
	 */
	DASHED_FLY("dashed_fly", "虚飞线");

	/**
	 * 代码
	 */
	@Getter
	private String code;

	/**
	 * 描述
	 */
	@Getter
	private String desc;

	LineType(String code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	/**
	 * 获取随机线型
	 *
	 * @param values 值
	 * @return {@code String}
	 */
	public static String getRandomValue(LineType[] values) {

		return values[(int) (Math.random() * values.length)].code;

	}

	/**
	 * 根据请求语言获取对应描述
	 *
	 * @return {@code String}
	 */
	public String getMsg() {
		return CommonContext.isChinese() ? desc : code;
	}

	/**
	 * 获取线型列表
	 *
	 * @return {@code List<LocalType>}
	 */
	public static List<LocalType> getMsgList(){
		return StreamApiUtils.map(Lists.newArrayList(LineType.values()),
				lineType -> new LocalType(lineType.getCode(), lineType.getMsg()));
	}
}
