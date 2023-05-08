package com.cyberpeace.cr.crbase.service.context.convert;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.mdparser.convert.MdConvertEnum;

/**
 * 资管训练类型转换
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public enum BusinessTypeConvert implements GenericEnum, MdConvertEnum<Integer, String> {

	SINGLE(1, "单兵训练"), TEAM(3, "团队训练"), REDVSBLUE(4, "红蓝对抗");

	private Integer code;

	private String desc;

	BusinessTypeConvert(Integer sceneType, String desc) {
		this.code = sceneType;
		this.desc = desc;
	}

	@Override
	public Integer to() {
		return code;
	}

	@Override
	public String reTo() {
		return desc;
	}

	@Override
	public Integer getCode() {
		return code;
	}

	@Override
	public String getDesc() {
		return desc;
	}
}
