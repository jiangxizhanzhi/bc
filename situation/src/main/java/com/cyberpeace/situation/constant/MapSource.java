package com.cyberpeace.situation.constant;

import java.util.List;

import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.situation.model.StringType;
import com.google.common.collect.Lists;

import lombok.Getter;

public enum MapSource {

	/**
	 * 地图源
	 */

	GGIM(1, "谷歌-影像", "google_image"),
	GGDL(2, "谷歌-矢量", "google_dlg"),
	GDIM(3, "高德-影像", "gaode_image"),
	GDDL(4, "高德-矢量", "gaode_dlg"),
	BDIM(5, "百度-影像", "tdt_image"),
	BDDL(6, "百度-矢量", "tdt_vector");

	@Getter
	private Integer code;

	@Getter
	private String desc;

	@Getter
	private String name;

	MapSource(Integer code, String desc, String name) {
		this.code = code;
		this.desc = desc;
		this.name = name;
	}

	public static List<StringType> getList() {
		return StreamApiUtils.map(Lists.newArrayList(MapSource.values()),
				mapSource -> new StringType(mapSource.getName(), mapSource.getDesc()));
	}

}
