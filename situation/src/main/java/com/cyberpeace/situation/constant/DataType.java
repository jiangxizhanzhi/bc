package com.cyberpeace.situation.constant;

import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.situation.model.IntType;
import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

public enum DataType {

	/**
	 * 地图源
	 */

	MAP(1, "地图数据");

	@Getter
	private Integer code;

	@Getter
	private String name;

	DataType(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public static List<IntType> getList(){
		return StreamApiUtils.map(Lists.newArrayList(DataType.values()),
				mapSource -> new IntType(mapSource.getCode(), mapSource.getName()));
	}

}
