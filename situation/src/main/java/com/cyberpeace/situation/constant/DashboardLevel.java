package com.cyberpeace.situation.constant;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;

public enum DashboardLevel {

	/**
	 * 大屏层级
	 */

	LGZX(1, "LG中心综合态势", 1),
	FBC(2, "分靶场综合态势", 1),
	RW(3, "任务态势", 2);

	@Getter
	private Integer code;

	@Getter
	private String name;

	@Getter
	private Integer type;

	DashboardLevel(Integer code, String name, Integer type) {
		this.code = code;
		this.name = name;
		this.type = type;
	}

	public static List<Integer> selectLevelByType(Integer type) {
		return Arrays.stream(DashboardLevel.values()).filter(e -> e.getType().equals(type))
				.map(DashboardLevel::getCode).collect(Collectors.toList());
	}

}
