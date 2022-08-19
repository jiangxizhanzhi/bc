package com.cyberpeace.common.mdparser.convert;

public enum SceneShareConvert implements MdConvertEnum<Integer, String> {

	PRIVATE(1, "否"),
	SHARE(2, "是");

	private Integer code;

	private String mode;

	SceneShareConvert(Integer code, String mode) {
		this.code = code;
		this.mode = mode;
	}

	@Override
	public Integer to() {
		return code;
	}

	@Override
	public String reTo() {
		return mode;
	}
}
