package com.cyberpeace.cr.crbase.service.context.convert;

import com.cyberpeace.common.mdparser.convert.MdConvertEnum;

public enum BusinessTypeConvert implements MdConvertEnum<Integer, String> {

	SINGLE(1, "单兵训练"),TEAM(3, "团队训练"),REDVSBLUE(4,"红蓝对抗");

	private Integer sceneType;

	private String desc;

	BusinessTypeConvert(Integer sceneType, String desc) {
		this.sceneType = sceneType;
		this.desc = desc;
	}

	@Override
	public Integer to() {
		return sceneType;
	}

	@Override
	public String reTo() {
		return desc;
	}
}
