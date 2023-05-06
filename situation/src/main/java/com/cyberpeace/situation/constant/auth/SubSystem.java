package com.cyberpeace.situation.constant.auth;

import java.util.List;

import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.situation.model.IntType;
import com.google.common.collect.Lists;

import lombok.Getter;

/**
 * 子系统
 *
 * @author chenhy
 * @date 2023/05/06
 */
public enum SubSystem {

	/**
	 * 想定子系统
	 */
	SS(1, "想定子系统"),

	/**
	 * 数据库子系统
	 */
	DBS(2, "数据库子系统"),

	/**
	 * 数据库挖掘子系统
	 */
	DBDS(3, "数据库挖掘子系统"),

	/**
	 * 配置管理子系统
	 */
	CNS(4, "配置管理子系统");

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

	SubSystem(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	/**
	 * 获取子系统列表
	 *
	 * @return {@code List<IntType>}
	 */
	public static List<IntType> getList() {
		return StreamApiUtils.map(Lists.newArrayList(SubSystem.values()),
				mapSource -> new IntType(mapSource.getCode(), mapSource.getName()));
	}

}
