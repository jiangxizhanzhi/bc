package com.cyberpeace.cr.crbase.service.context.convert;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.mdparser.convert.MdConvertEnum;

/**
 * 资管训练类型转换
 *
 * @author SN
 * @date 2024/11/08
 */
public enum BusinessTypeConvert implements GenericEnum, MdConvertEnum<Integer, String> {

	/**
	 * 单
	 */
	SINGLE(1, "单兵训练"),
	/**
	 * 团队
	 */
	TEAM(3, "团队训练"),
	/**
	 * 红vs蓝
	 */
	REDVSBLUE(4,"红蓝对抗");

	/**
	 * 代号
	 */
	private Integer code;

	/**
	 * 描述
	 */
	private String desc;

	/**
	 * 业务类型转换
	 *
	 * @param sceneType 场景类型
	 * @param desc      描述
	 */
	BusinessTypeConvert(Integer sceneType, String desc) {
		this.code = sceneType;
		this.desc = desc;
	}

	/**
	 * 自
	 *
	 * @return {@code Integer }
	 */
	@Override
	public Integer to() {
		return code;
	}

	/**
	 * re 更改为
	 *
	 * @return {@code String }
	 */
	@Override
	public String reTo() {
		return desc;
	}

	/**
	 * 获取代号
	 *
	 * @return {@code Integer }
	 */
	@Override
	public Integer getCode() {
		return code;
	}

	/**
	 * 获取描述
	 *
	 * @return {@code String }
	 */
	@Override
	public String getDesc() {
		return desc;
	}
}
