package com.cyberpeace.situation.constant;

import java.util.Arrays;
import java.util.List;

import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.situation.model.StringType;
import com.google.common.collect.Lists;

import lombok.Getter;

/**
 * 地图源
 *
 * @author chenhy
 * @date 2023/05/06
 */
public enum MapSource {

	/**
	 * 谷歌-影像
	 */
	GGIM(1, "谷歌-影像", "google_image"),

	/**
	 * 谷歌-矢量
	 */
	GGDL(2, "谷歌-矢量", "google_dlg"),

	/**
	 * 高德-影像
	 */
	GDIM(3, "高德-影像", "gaode_image"),

	/**
	 * 高德-矢量
	 */
	GDDL(4, "高德-矢量", "gaode_dlg"),

	/**
	 * 百度-影像
	 */
	BDIM(5, "百度-影像", "tdt_image"),

	/**
	 * 百度-矢量
	 */
	BDDL(6, "百度-矢量", "tdt_vector");

	/**
	 * 代码
	 */
	@Getter
	private Integer code;

	/**
	 * 描述
	 */
	@Getter
	private String desc;

	/**
	 * 名称
	 */
	@Getter
	private String name;

	MapSource(Integer code, String desc, String name) {
		this.code = code;
		this.desc = desc;
		this.name = name;
	}

	/**
	 * 获取地图源列表
	 *
	 * @return {@code List<StringType>}
	 */
	public static List<StringType> getList() {
		return StreamApiUtils.map(Lists.newArrayList(MapSource.values()),
				mapSource -> new StringType(mapSource.getName(), mapSource.getDesc()));
	}

	/**
	 * 通过代码获取地图源
	 *
	 * @param code 代码
	 * @return {@code MapSource}
	 */
	public static MapSource getMapSourceByCode(Integer code) {
		return Arrays.stream(MapSource.class.getEnumConstants())
				.filter(t -> t.getCode().equals(code)).findFirst().orElse(MapSource.GGIM);
	}

}
