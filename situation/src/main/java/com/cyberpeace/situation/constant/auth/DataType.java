package com.cyberpeace.situation.constant.auth;

import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.situation.model.IntType;
import com.google.common.collect.Lists;
import lombok.Getter;

import java.util.List;

/**
 * 数据类型 - 访问权限控制
 *
 * @author chenhy
 * @date 2023/05/06
 */
public enum DataType {

	/**
	 * 地图数据
	 */
	MAP(1, "地图数据");

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

	DataType(Integer code, String name) {
		this.code = code;
		this.name = name;
	}

	/**
	 * 获取数据类型列表
	 *
	 * @return {@code List<IntType>}
	 */
	public static List<IntType> getList(){
		return StreamApiUtils.map(Lists.newArrayList(DataType.values()),
				mapSource -> new IntType(mapSource.getCode(), mapSource.getName()));
	}

}
