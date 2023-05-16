package com.cyberpeace.situation.constant.topo;

import java.util.Arrays;
import java.util.List;

import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.situation.model.LocalType;
import com.google.common.collect.Lists;

import lombok.Getter;

/**
 * 颜色
 *
 * @author chenhy
 * @date 2023/05/06
 */
public enum Color {

	/**
	 * 红色
	 */
	RED("#FF0000", "红色", "red"),

	/**
	 * 黄色
	 */
	YELLOW("#FFFF00", "黄色", "yellow"),

	/**
	 * 蓝色
	 */
	BLUE("#0000FF", "蓝色", "blue"),

	/**
	 * 白色
	 */
	WHITE("#FFFFFF", "白色", "white"),

	/**
	 * 黑色
	 */
	GOLD("#CD7F32", "金色", "gold"),

	/**
	 * 绿色
	 */
	GREEN("#00FF00", "绿色", "green"),

	/**
	 * 灰色
	 */
	GREY("#1B1B1B", "灰色", "grey");

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

	/**
	 * 英文描述
	 */
	@Getter
	private String enDesc;

	Color(String code, String desc, String enDesc) {
		this.code = code;
		this.desc = desc;
		this.enDesc = enDesc;
	}

	/**
	 * 获取随机颜色的代码
	 *
	 * @param values 值
	 * @return {@code String}
	 */
	public static String getRandomValue(Color[] values) {

		return values[(int) (Math.random() * values.length)].code;

	}

	/**
	 * 根据请求语言获取对应描述
	 *
	 * @return {@code String}
	 */
	public String getMsg() {
		return CommonContext.isChinese() ? desc : enDesc;
	}

	/**
	 * 获取颜色列表
	 *
	 * @return {@code List<LocalType>}
	 */
	public static List<LocalType> getMsgList() {
		return StreamApiUtils.map(Lists.newArrayList(Color.values()),
				color -> new LocalType(color.getCode(), color.getMsg()));
	}

	public static Color getColorByCode(String code) {
		return Arrays.stream(Color.class.getEnumConstants()).filter(t -> t.getCode().equals(code))
				.findFirst().orElse(Color.WHITE);
	}
}
