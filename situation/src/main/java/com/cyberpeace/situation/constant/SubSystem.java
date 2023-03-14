package com.cyberpeace.situation.constant;

import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.situation.model.IntType;
import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

public enum SubSystem {

	/**
	 * 地图源
	 */

	SS(1, "想定子系统"), DBS(2, "数据库子系统"), DBDS(3, "数据库挖掘子系统"),CNS(4, "配置管理子系统");

	@Getter
	private Integer code;

	@Getter
	private String name;

	SubSystem(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public static List<IntType> getList(){
		return StreamApiUtils.map(Lists.newArrayList(SubSystem.values()),
				mapSource -> new IntType(mapSource.getCode(), mapSource.getName()));
	}

}
