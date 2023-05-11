package com.cyberpeace.situation.constant.gis;

import java.util.Arrays;
import java.util.List;

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
	GGIM(1, "谷歌-影像", "google_image", 1),

	/**
	 * 谷歌-矢量
	 */
	GGDL(2, "谷歌-矢量", "google_dlg", 2),

	/**
	 * 高德-影像
	 */
	GDIM(3, "高德-影像", "gaode_image", 1),

	/**
	 * 高德-矢量
	 */
	GDDL(4, "高德-矢量", "gaode_dlg", 2),

	/**
	 * 百度-影像
	 */
	BDIM(5, "百度-影像", "tdt_image", 1),

	/**
	 * 百度-矢量
	 */
	BDDL(6, "百度-矢量", "tdt_vector", 2);

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

	/**
	 * 类型
	 */
	@Getter
	private Integer type;

	MapSource(Integer code, String desc, String name, Integer type) {
		this.code = code;
		this.desc = desc;
		this.name = name;
		this.type = type;
	}

	/**
	 * 获取地图源列表
	 *
	 * @return {@code List<StringType>}
	 */
	public static List<StringType> getList(Integer type) {

		// 构造地图源列表
		List<StringType> maoSourceList = Lists.newArrayList();

		for (MapSource mapSource : MapSource.values()) {
			// 如果类型为空或者类型相同，则添加
			if (type == null || mapSource.getType().equals(type)) {
				maoSourceList.add(new StringType(mapSource.getName(), mapSource.getDesc()));
			}
		}
		return maoSourceList;
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
