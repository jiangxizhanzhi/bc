package com.cyberpeace.situation.constant;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

/**
 * 大屏层级
 *
 * @author chenhy
 * @date 2023/05/06
 */
public enum DashboardLevel {

	/**
	 * LG中心综合态势
	 */
	LGZX(1, "LG中心综合态势", 1),

	/**
	 * 分靶场综合态势
	 */
	FBC(2, "分靶场综合态势", 1),

	/**
	 * 任务态势
	 */
	RW(3, "任务态势", 2);

	/**
	 * 代码
	 */
	@Getter
	private Integer code;

	/**
	 * 名称
	 */
	@Getter
	private String name;

	/**
	 * 类型 1-综合态势 2-任务态势
	 */
	@Getter
	private Integer type;

	DashboardLevel(Integer code, String name, Integer type) {
		this.code = code;
		this.name = name;
		this.type = type;
	}

	/**
	 * 根据类型返回层级列表
	 *
	 * @param type 类型
	 * @return {@code List<Integer>}
	 */
	public static List<Integer> selectLevelByType(Integer type) {
		return Arrays.stream(DashboardLevel.values()).filter(e -> e.getType().equals(type))
				.map(DashboardLevel::getCode).collect(Collectors.toList());
	}

}
