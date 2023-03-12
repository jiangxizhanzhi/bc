package com.cyberpeace.situation.constant;

import java.util.List;

import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.situation.model.IntType;
import com.google.common.collect.Lists;

import lombok.Getter;

public enum MapSource {

	/**
	 * 地图源
	 */

	GG(1, "谷歌"), BD(2, "百度"), GD(3, "高德");

	@Getter
	private Integer code;

	@Getter
	private String name;

	MapSource(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	public static List<IntType> getList(){
		return StreamApiUtils.map(Lists.newArrayList(MapSource.values()),
				mapSource -> new IntType(mapSource.getCode(), mapSource.getName()));
	}

}
